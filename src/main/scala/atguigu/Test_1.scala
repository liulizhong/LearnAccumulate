package atguigu

import scala.collection.mutable

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
    val ch = 'V'
    var res = ch match {
      case '+' => println("ok~")
      // 1. mychar = ch  2.  然后再去匹配  3. 这样的语法，我们称为模式中的变量
      case mychar => println("ok~" + mychar)
      case _ => println ("ok~~")
    }
    res = ()
  }
}