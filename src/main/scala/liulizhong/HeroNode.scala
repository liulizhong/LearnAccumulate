package liulizhong

/**
  * @class ??
  * @CalssName HeroNode
  * @author lizhong.liu 
  * @create 2020-06-04 17:31
  * @Des TODO
  * @version TODO
  */
class HeroNode(hNo: Int, hName: String, hNickname: String) {
  var no: Int = hNo
  var name: String = hName
  var nickname: String = hNickname
  var next: HeroNode = null //next 默认为null
}

