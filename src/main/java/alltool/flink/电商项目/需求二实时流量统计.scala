package alltool.flink.电商项目

import java.net.URL
import java.sql.Timestamp
import java.text.SimpleDateFormat

import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.api.common.state.{ListState, ListStateDescriptor, MapState, MapStateDescriptor}
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

import scala.collection.mutable.ListBuffer

/**
  * @class 需求二：实时流量统计（实现方法基本和需求已完全相同）
  *        -- 10分钟小时窗口大小滑动5秒钟，实时展示这十分钟内url点击量前五的url
  * @CalssName UserBehavior
  * @author lizhong.liu
  * @create 2020-10-14 16:11
  * @Des TODO
  * @version TODO
  */

// 输入数据样例类
case class ApacheLogEvent(ip: String, userId: String, eventTime: Long, method: String, url: String)

// 窗口聚合结果样例类
case class UrlViewCount(url: String, windowEnd: Long, count: Long)

object NetworkFlow {
  def main(args: Array[String]): Unit = {
    //## 1. 创建执行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    //## 2. 读取数据 + 处理数据
    val dataStream: DataStream[UrlViewCount] = env.readTextFile("C:\\workhouse\\LearnAccumulate\\src\\main\\java\\alltool\\flink\\电商项目\\resources\\apache.log")
      //    val dataStream = env.socketTextStream("localhost", 7777)
      .map(data => {
      val dataArray = data.split(" ")
      // 定义时间转换
      val simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss")
      val timestamp = simpleDateFormat.parse(dataArray(3).trim).getTime
      ApacheLogEvent(dataArray(0).trim, dataArray(1).trim, timestamp, dataArray(5).trim, dataArray(6).trim)
    })
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[ApacheLogEvent](Time.seconds(1)) { // 数据乱序情况处理就不能直接用assignAscendingTimestamps指定watermark，可以给个1秒的允许延迟
        override def extractTimestamp(element: ApacheLogEvent): Long = element.eventTime
      })
      .keyBy(_.url)
      .timeWindow(Time.minutes(10), Time.seconds(5))
      .allowedLateness(Time.seconds(60))
      .aggregate(new UrlCountAgg(), new UrlWindowResult())

    val processedStream = dataStream
      .keyBy(_.windowEnd)
      .process(new TopNHotUrls(5))

//    dataStream.print("aggregate")
    processedStream.print("process")

    env.execute("network flow job")
  }
}

// 自定义预聚合函数
class UrlCountAgg() extends AggregateFunction[ApacheLogEvent, Long, Long] {
  override def add(value: ApacheLogEvent, accumulator: Long): Long = accumulator + 1
  override def createAccumulator(): Long = 0L
  override def getResult(accumulator: Long): Long = accumulator
  override def merge(a: Long, b: Long): Long = a + b
}

// 自定义窗口处理函数
class UrlWindowResult() extends WindowFunction[Long, UrlViewCount, String, TimeWindow] {
  override def apply(key: String, window: TimeWindow, input: Iterable[Long], out: Collector[UrlViewCount]): Unit = {
    out.collect(UrlViewCount(key, window.getEnd, input.iterator.next()))
  }
}

// 自定义排序输出处理函数
class TopNHotUrls(topSize: Int) extends KeyedProcessFunction[Long, UrlViewCount, String] {
  lazy val urlState: MapState[String, Long] = getRuntimeContext.getMapState(new MapStateDescriptor[String, Long]("url-state", classOf[String], classOf[Long]))

  override def processElement(value: UrlViewCount, ctx: KeyedProcessFunction[Long, UrlViewCount, String]#Context, out: Collector[String]): Unit = {  // 同需求一情况
    urlState.put(value.url, value.count)
    ctx.timerService().registerEventTimeTimer(value.windowEnd + 1)
  }

  override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[Long, UrlViewCount, String]#OnTimerContext, out: Collector[String]): Unit = {
    // 从状态中拿到数据
    val allUrlViews: ListBuffer[(String, Long)] = new ListBuffer[(String, Long)]()
    val iter = urlState.entries().iterator()
    while (iter.hasNext) {
      val entry = iter.next()
      allUrlViews += ((entry.getKey, entry.getValue))
    }

    //    urlState.clear()

    val sortedUrlViews = allUrlViews.sortWith(_._2 > _._2).take(topSize)

    // 格式化结果输出
    val result: StringBuilder = new StringBuilder()
    result.append("时间：").append(new Timestamp(timestamp - 1)).append("\n")
    for (i <- sortedUrlViews.indices) {
      val currentUrlView = sortedUrlViews(i)
      result.append("NO").append(i + 1).append(":")
        .append(" URL=").append(currentUrlView._1)
        .append(" 访问量=").append(currentUrlView._2).append("\n")
    }
    result.append("=============================")
    Thread.sleep(1000)
    out.collect(result.toString())
  }
}