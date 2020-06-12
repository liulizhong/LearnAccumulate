package liulizhong.sort

import java.text.SimpleDateFormat
import java.util.Date

/**
  * @class ??
  * @CalssName MaoPao
  * @author lizhong.liu 
  * @create 2020-06-12 16:36
  * @Des TODO
  * @version TODO
  */
object MaoPao {
  def main(args: Array[String]): Unit = {
    val rand = new util.Random(80000)
    val arr = new Array[Int](80000)
    for (i <- 0 to 79999) {
      arr(i) = rand.nextInt()
    }
    println("排序前：")
    val now: Date = new Date()
    val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = dateFormat.format(now)
    println("排序前时间： " + date) //输出时间
    //    maoPaoSort(arr)
    xuanze(arr)
    println("排序后：")
    val now2: Date = new Date()
    val date2 = dateFormat.format(now2)
    println("排序后时间： " + date2) //输出时间
  }

  //冒泡排序
  def maoPaoSort(arr: Array[Int]): Unit = {
    for (i <- 0 to arr.length - 2) {
      for (j <- 0 to arr.length - 2 - i) {
        if (arr(j) > arr(j + 1)) {
          val tmp = arr(j)
          arr(j) = arr(j + 1)
          arr(j + 1) = tmp
        }
      }
    }
  }

  //选择排序
  def xuanze(arr: Array[Int]): Unit = {

    for (i <- 0 until arr.length - 1) {
      var min = arr(i)
      var minIndex = i
      //遍历
      for (j <- (i + 1) until arr.length) {
        if (min > arr(j)) { // 说明min不是真的最小值
          min = arr(j) // 重置min
          minIndex = j // 重置minIndex
        }
      }
      //判断是否需要交换
      if (minIndex != i) {
        arr(minIndex) = arr(i)
        arr(i) = min
      }

      //      println(s"第${i+1}轮结束")
      //      println(arr.mkString(" "))
    }
  }
}
