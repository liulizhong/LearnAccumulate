package alltool.spark.project.offline

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import java.util.{Date, Random, ResourceBundle}
import com.alibaba.fastjson.JSON

/**
  * @class ??
  * @CalssName One_CategoryTop10Application
  * @author lizhong.liu 
  * @create 2020-07-10 10:31
  * @Des TODO
  * @version TODO
  */
object One_CategoryTop10Application {
  def main(args: Array[String]): Unit = {
    // 1、创建Spark、SparkSession配置对象，并导入隐式转换
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("CategoryTop10Application")
    val spark = SparkSession.builder().config(sparkConf).enableHiveSupport().getOrCreate() // 启用HiveSupport
    import spark.implicits._
    // 2、读取动作日志
    spark.sql("use sparkmall")
    val sql = "select * from user_visit_action where 1 = 1 "
    // 利用java.util.ResourceBundle工具读取到配置文件信息，并alibaba的JSON解析工具解析出各信息
    val jsonConfig: String = ResourceBundle.getBundle("condition").getString("condition.params.json") // 获取配置信息
    val jsonObject = JSON.parseObject(jsonConfig)
    val startDate = jsonObject.getString("startDate")
    val endDate = jsonObject.getString("endDate")
    println("startDate:" + startDate)

  }
}
