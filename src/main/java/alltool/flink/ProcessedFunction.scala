package alltool.flink

import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.streaming.api.scala.{DataStream, OutputTag, StreamExecutionEnvironment}
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.functions.{KeyedProcessFunction, ProcessFunction}
import org.apache.flink.util.Collector

/**
  * @class 利用ProcessedFunction
  *        解决需求1：同一设备温度再10秒内一直上升的，便报警【知识点 ProcessFunction】
  *        解决需求2：温度值低于32F的温度输出到side output【知识点 ProcessFunction + 测输出流】
  * @CalssName ProcessedFunction
  * @author lizhong.liu 
  * @create 2020-09-18 11:52
  * @Des TODO
  * @version TODO
  */
object ProcessedFunction {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    val inputStream: DataStream[String] = env.socketTextStream("192.168.1.241", 7777)
    val dataStream: DataStream[SensorReading] = inputStream.map(data => {
      val dataArray: Array[String] = data.split(",")
      new SensorReading(dataArray(0).trim, dataArray(1).trim.toLong, dataArray(2).trim.toDouble)
    })
    val keyProcessStream: DataStream[String] = dataStream.keyBy(_.id).process(new MyTempincreWarning())
    keyProcessStream.print("processtest:")
    val higeTempStream: DataStream[SensorReading] = dataStream.process(new MyOutSidealert())
    higeTempStream.print("high: ")
    val lowTempStream: DataStream[String] = higeTempStream.getSideOutput(new OutputTag[String]("freezing"))
    lowTempStream.print("low: ")
    env.execute("process function test!!")
  }
}

// 需求一：自定义KeyedProcessFunction类，解决10秒内温度持续升高报警的需求
class MyTempincreWarning() extends KeyedProcessFunction[String, SensorReading, String] { //参数类型：[key，输入类型，输出类型]
  // 首先定义状态，用来保存上一次的温度值，以及已经核定的定时器时间戳
  private lazy val lastTempState: ValueState[Double] = getRuntimeContext.getState(new ValueStateDescriptor[Double]("last-temp", classOf[Double]))
  private lazy val currentTimerState: ValueState[Long] = getRuntimeContext.getState(new ValueStateDescriptor[Long]("last-currenttime", classOf[Long]))

  // 处理每一个元素的方法
  override def processElement(value: SensorReading, ctx: KeyedProcessFunction[String, SensorReading, String]#Context, out: Collector[String]): Unit = {
    // value：数据， ctx上下文对象， out输出。（ctx可以获取测输出、时间戳、key、还能调时间服务【时间服务能获取处理时间、watermark、还能注册定时器、还能删除定时器提醒】）
    val lastTemp: Double = lastTempState.value() //获取上次的温度值
    val lastCurrentTime: Long = currentTimerState.value() //获取上次的定时器时间戳
    lastTempState.update(value.temperature) //将状态更新为最新温度
    if (lastTemp < value.temperature && lastCurrentTime == 0) {
      val ts: Long = ctx.timerService().currentProcessingTime() + 10000L //获取此条数据的时间戳
      ctx.timerService().registerProcessingTimeTimer(ts) //注册当前key的processing time的定时器。当processing time到达定时时间时，触发timer
      currentTimerState.update(ts)
    } else if (lastTemp <= value.temperature) {
      ctx.timerService().deleteProcessingTimeTimer(currentTimerState.value())
      currentTimerState.clear()
    }
  }

  //
  override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[String, SensorReading, String]#OnTimerContext, out: Collector[String]): Unit = {
    out.collect("sensor " + ctx.getCurrentKey + "温度在10秒内持续升高！！！")
    currentTimerState.clear()
  }
}

//需求二：
class MyOutSidealert extends ProcessFunction[SensorReading, SensorReading] {
  override def processElement(value: SensorReading, ctx: ProcessFunction[SensorReading, SensorReading]#Context, out: Collector[SensorReading]): Unit = {
    if (value.temperature < 32) {
      ctx.output(new OutputTag[String]("freezing"), "freezing alert for " + value.id)
    } else {
      out.collect(value)
    }
  }
}