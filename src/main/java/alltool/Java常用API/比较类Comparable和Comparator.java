package alltool.Java常用API;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * @author lizhong.liu
 * @version TODO
 * @class ??
 * @CalssName 比较类Comparable和Comparator
 * @create 2021-03-09 11:02
 * @Des TODO
 */
public class 比较类Comparable和Comparator {
    public static void main(String[] args) {

        //// 【1】、元素实现Comparable接口，即实现了比较规则，则可以添加到有序集合中
        TreeSet set = new TreeSet();
        set.add(new MyStudents(7));
        set.add(new MyStudents(5));
        set.add(new MyStudents(7));
        set.add(new MyStudents(8));
        for (Object object : set) {
            System.out.println(object);
        }

        //// 【2】、有序集合传入Comparator比较规则，即实现了比较规则，则可以添加制定规则的元素到有序集合
        TreeSet<MyStudents> myStudentsTreeSet = new TreeSet<>(new Comparator<MyStudents>() {
            @Override
            public int compare(MyStudents o1, MyStudents o2) {
                return o1.age - o2.age;
            }
        });
        myStudentsTreeSet.add(new MyStudents(2));
        myStudentsTreeSet.add(new MyStudents(1));
        myStudentsTreeSet.add(new MyStudents(3));
        myStudentsTreeSet.add(new MyStudents(2));
        for (MyStudents myStudents : myStudentsTreeSet) {
            System.out.println(myStudents);
        }
    }
}

class MyStudents implements Comparable<MyStudents> {
    int age;
    public MyStudents(int age) {
        this.age = age;
    }
    @Override
    public int compareTo(MyStudents o) {
        return this.age - o.age;
    }
    @Override
    public String toString() {
        return "MyStudents{" + "age=" + age + '}';
    }
}