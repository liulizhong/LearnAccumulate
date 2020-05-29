package liulizhong

import java.text.SimpleDateFormat
import java.util.Date
import org.hibernate.validator.internal.constraintvalidators.bv.MaxValidatorForCharSequence
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
    val list = List[Int](1,2,3,5,8,8,6,4,2,1,9,3,2,1,5,7,9,3,1,3,5)
      val max_list = maxnum(list)
      println(max_list)
  }

  def maxnum(list: List[Int]):Int={
    if (list.size == 1){
      return list.head
    }
    if (list.head > maxnum(list.tail)){
      return list.head
    }
    maxnum(list.tail)
  }
}