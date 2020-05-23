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
    val dog = new Dog
    dog + 10
    dog.+(90)
    println(dog.age) // 110

    //后置操作符的使用
    dog-- //  109

    dog++ //110

    dog-- // 109

    dog++


    println(dog.age) // ?

    !dog
    println("dog.age=" + dog.age) //?

  }

  class Dog {
    var age = 10

    def +(n: Int): Unit = {
      this.age += n
    }

    def --(): Unit = {
      this.age -= 1
    }

    //后置操作符
    def ++(): Unit = {
      this.age += 1
    }

    def unary_!(): Unit = {
      this.age = -this.age
    }
  }

}