package liulizhong.customerinformation

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
  * @class 服务类
  * @CalssName service
  * @author lizhong.liu
  * @create 2020-05-29 14:34
  * @Des TODO
  * @version TODO
  */
class ServiceConsumer {
  val consumers: ListBuffer[Consumer] = ListBuffer(new Consumer(1, "张三", '男', 29, "110110", "123@321.com"))
  var total: Int = _;

  /**
    * 1、获取所有团队成员
    *
    * @return
    */
  def getConsumer() = {
    this.consumers
  }

  /**
    * 2、新增员工
    *
    * @param consumer
    * @return
    */
  def add(consumer: Consumer): Boolean = {
    val startlength = this.consumers.length
    this.consumers.append(consumer)
    if (this.consumers.length - startlength == 1) {
      total += 1
      return true
    } else {
      return false
    }
  }

  /**
    * 3、输入要删除的id，返回删除结果true/false
    *
    * @param id
    * @return
    */
  def del(id: Int): Boolean = {
    var index = -1;
    for (i <- 0 to this.consumers.length -1) {
      if (this.consumers(i).id == id) {
        index = i
        this.consumers.remove(i)
      }
    }
    if (index == -1) {
      return false
    } else {
      return true
    }
  }

  /**
    * 4、修改员工信息
    *
    * @param consumer
    * @return
    */
  def update(consumer: Consumer): Boolean = {
    var index = -1
    for (i <- 0 to consumers.length -1) {
      if (consumers(i).id == consumer.id) {
        index = consumer.id
        this.consumers(i) = consumer
      }
    }
    if (index == -1) {
      return false
    } else {
      return true
    }
  }

  def getConsumerByid(id: Int): Consumer = {
    var index = -1;
    for (i <- 0 to consumers.length - 1) {
      if (consumers(i).id == id) {
        index = i
      }
    }
    if (index == -1) {
      return null
    } else {
      return consumers(index)
    }
  }


}
