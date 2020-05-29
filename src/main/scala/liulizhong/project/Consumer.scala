package liulizhong.project

/**
  * @class ??
  * @CalssName Consumer
  * @author lizhong.liu 
  * @create 2020-05-27 11:54
  * @Des TODO
  * @version TODO
  */
class Consumer {
  var id: Int = _;
  var name: String = _
  var gender: Char = _
  var age: Int = _
  var phone: String = _
  var email: String = _

  def this(id: Int, name: String, gender: Char, age: Short, tel: String, email: String) {
    this
    this.id = id;
    this.age = age
    this.name = name
    this.gender = gender
    this.phone = phone
    this.email = email
  }

  override def toString = s"Consumer($id, $name, $gender, $age, $phone, $email)"
}
