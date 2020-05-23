package atguigu


import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  * @class ??
  * @CalssName Test_1
  * @author lizhong.liu 
  * @create 2020-05-14 11:14
  * @Des TODO
  * @version TODO
  */

object ApplyDemo {
  def main(args: Array[String]): Unit = {
    val list1 = new ArrayBuffer[Int]()
    list1.append(1, 3, 5, 7)
    println(list1)
    for (i <- 0 to list1.length - 1) {
      list1(i) *= 2
    }
    println(list1)
    val list2 = list1.map(x => 2*x)
    println(list2)
    val list3 = list2.map(f1)
    println(list3)

    println("==========================")
    val names = List("Alice", "Bob", "Nick")
    val namess = names.flatMap(name => {println("xxx~~~" ); name.toUpperCase})
    println(namess)
    println(namess.length)

    println("==========================")
    val namefilter = names.filter(str => str.startsWith("A"))
    println(namefilter)
    println("Asdfsf".startsWith("A"))
  }

  def f1(num:Int)={
    2*num
  }

}