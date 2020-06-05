package liulizhong

import util.control.Breaks._

/**
  * @class 定义我们的单向链表管理Hero
  * @CalssName SingleLinkedList
  * @author lizhong.liu 
  * @create 2020-06-04 17:32
  * @Des TODO
  * @version TODO
  */
class SingleLinkedList {
  val head = new HeroNode(0, "", "")

  def del(no: Int) = {
    var tmp = head
    var flag = false
    breakable {
      while (true) {
        if (tmp.next == null) {
          break()
        }
        if (tmp.no == no) {
          flag = true
          break()
        }
        tmp = tmp.next
      }
      if (flag) {
        tmp.next = tmp.next.next
      } else {
        printf("要删除的no=%d 不存在\n", no)
      }
    }
  }

  def update(newHeroNode: HeroNode) {
    if (head.next == null) {
      println("链表为空")
      return
    }
    var temp = head.next
    var flag = false
    breakable {
      while (true) {
        if (temp == null) {
          break()
        }
        if (temp.no == newHeroNode.no) {
          //找到.
          flag = true
          break()
        }
        temp = temp.next //
      }
      //判断是否找到
      if (flag) {
        temp.name = newHeroNode.name
        temp.nickname = newHeroNode.nickname
      } else {
        printf("没有找到 编号为%d 节点，不能修改\n", newHeroNode.no)
      }
    }
  }

  def add(heroNode: HeroNode): Unit = {
    //因为头结点不能动, 因此我们需要哟有一个临时结点，作为辅助
    var temp = head
    //找到链表的最后
    breakable {
      while (true) {
        if (temp.next == null) { //说明temp已经是链表最后
          break()
        }
        //如果没有到最后
        temp = temp.next
      }
    }
    //当退出while循环后，temp就是链表的最后
    temp.next = heroNode
  }


}
