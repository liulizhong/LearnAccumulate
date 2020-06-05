package liulizhong.customerinformation

import scala.io.StdIn

/**
  * @class ??
  * @CalssName ViewConsumer
  * @author lizhong.liu 
  * @create 2020-05-29 15:55
  * @Des TODO
  * @version TODO
  */
class ViewConsumer {
  private val serviceConsumer = new ServiceConsumer()
  var choose: Char = _
  var quit: Boolean = true

  def view(): Unit = {
    do {
      println("-----------------客户信息管理软件-----------------")
      println("                 1 添 加 客 户")
      println("                 2 修 改 客 户")
      println("                 3 删 除 客 户")
      println("                 4 客 户 列 表")
      println("                 5 退       出")
      println("请选择(1-5)：")
      choose = StdIn.readChar()
      choose match {
        case '1' => this.add()
        case '2' => this.update()
        case '3' => this.del()
        case '4' => this.getAllConsumer()
        case '5' => this.exit()
        case _ => println("输入错误请重新输入！！！！！！")
      }
    } while (quit)
    println("退出成功")
  }

  /**
    * 退出功能
    */
  def exit(): Unit = {
    while (true) {
      print("确认是否退出？y/n")
      var cho = StdIn.readChar()
      if (cho == 'y' || cho == 'Y') {
        quit = false
        return
      } else if (cho == 'n' || cho == 'N') {
        println("已取消推出")
        return
      } else {
        println("选择错误，重新选择！！！！！！！！！！！！！！！")
      }
    }
  }

  /**
    * 获取所有用户列表
    */
  def getAllConsumer(): Unit = {
    val consumer = serviceConsumer.getConsumer()
    for (elem <- consumer) {
      println(elem)
    }
  }

  /**
    * 删除指定用户
    */
  def del(): Unit = {
    print("请输入要删除的用户的id：")
    val id = StdIn.readInt()
    val bool = serviceConsumer.del(id)
    if (bool) {
      println("删除成功")
      return
    } else {
      println("删除失败，用户不存在！！！！！！！！！！")
    }
  }

  /**
    * 更新用户
    */
  def update(): Unit = {
    getAllConsumer()
    print("选择要更新的用户id：")
    val id = StdIn.readInt()
    val consumer = serviceConsumer.getConsumerByid(id)
    if (consumer == null) {
      println("id不存在！！！！！")
    } else {
      print("姓名：" + consumer.name)
      val name = StdIn.readLine()
      print("性别：" + consumer.gender)
      val gender = StdIn.readChar()
      print("年龄：" + consumer.age)
      val age = StdIn.readShort()
      print("电话：" + consumer.phone)
      val tel = StdIn.readLine()
      print("邮箱：" + consumer.email)
      val email = StdIn.readLine()
      //构建对象
      val newConsumer = new Consumer(id, name, gender, age, tel, email)
      val bool = serviceConsumer.update(newConsumer)
      if (bool) {
        println("添加成功！！！！")
      } else {
        println("添加失败！！！！")
      }
    }
  }

  /**
    * 新增用户
    */
  def add(): Unit = {
    println()
    println("---------------------添加客户---------------------")
    print("id:")
    val id = StdIn.readInt()
    println("姓名：")
    val name = StdIn.readLine()
    println("性别：")
    val gender = StdIn.readChar()
    println("年龄：")
    val age = StdIn.readShort()
    println("电话：")
    val tel = StdIn.readLine()
    println("邮箱：")
    val email = StdIn.readLine()
    //构建对象
    val customer = new Consumer(id, name, gender, age, tel, email)
    serviceConsumer.add(customer)
    println("---------------------添加完成---------------------")

  }

}
