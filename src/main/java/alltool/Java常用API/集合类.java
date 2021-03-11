package alltool.Java常用API;

import java.io.IOException;
import java.util.*;

public class 集合类 {
    public static void main(String[] args) throws IOException {

        //// 【1】、List分类[Collection]
        Vector vectors = new Vector<>();                        // 动态数组。相对 ArrayList 线程安全。默认是照 2 倍扩容(10-20-40-80)。多线程共享变量情况建议用。
        ArrayList arrayLists = new ArrayList<>();               // 动态数组。相对  Vector 线程不安全。默认是照1.5倍扩容(10-15-22-33)。无元素进出顺序或需要index建议用。
        LinkedList linkedLists = new LinkedList();              // 双向链表。队列(先进先出offer/poll)，底层链表结构，删除插入效率高。无index，有元素进出顺序需要建议用。
        Stack stacks = new Stack();                             // Vector的子类。增加了添加顺序，队列（后进先出pop/push）。需要添加顺序倒序情况建议用。

        //// 【2】、Set分类[Collection]
        HashSet hashSet = new HashSet();                        // 通过元素的hashCode()和equals()方法，保证无序，不可重复。可重写equals方法实现“自定义规则不重复”
        TreeSet treeSet = new TreeSet();                        // 不重复且有序。要求元素要么实现Comparable接口，要么传入Comparator比较器规则
        LinkedHashSet linkedHashSet = new LinkedHashSet();      // HashSet的子类，多维护了元素之间添加的前后顺序。相对添加和删除效率也会较低

        //// 【3】、Collection常用方法
        arrayLists.add("a");                       // Collection     // 添加一个元素
        arrayLists.addAll(vectors);                // Collection     // 添加一个集合里的所有元素
        arrayLists.remove("b");                // Collection     // 删除一个元素
        arrayLists.removeAll(linkedLists);         // Collection     // 删除一个集合里存在的所有元素
        arrayLists.contains(3);                    // Collection     // 是否包含元素 数字3
        arrayLists.containsAll(stacks);            // Collection     // 断c是否是当前集合的子集，就是当前集合是否全部包含c集合的所有元素
        arrayLists.retainAll(linkedLists);         // Collection     // arrayLists只保留两个集合的交集部分
        arrayLists.size();                         // Collection     // 返回集合的元素个数
        arrayLists.isEmpty();                      // Collection     // 判断集合是不是空集合
        arrayLists.clear();                        // Collection     // 清空当前集合的所有元素
        arrayLists.iterator();                     // Collection     // 获取当前集合的一个迭代器对象，用于遍历当前集合用
        arrayLists.toArray();                      // Collection     // 把当前集合中的元素放到一个数组中，并返回
//        arrayLists.set(0,"c");                     // List特有      // 替换当前集合index位置的元素
//        arrayLists.get(1);                         // List特有       // 获取index位置的元素
//        arrayLists.indexOf("a");                   // List特有      // 获取“a”元素在当前集合中的第一次出现的索引，若没有返回-1
//        arrayLists.lastIndexOf("a");           // List特有      // 获取“a”元素在当前集合中的最后次出现的索引，若没有返回-1
//        arrayLists.subList(2,4);                   // List特有      // ：截取当前集合[fromIndex,toIndex)部分的元素。返回一个List

        //// 【4】、Collection集合的三种遍历
        for (Object arrayList : arrayLists) {                           // 遍历一：foreach遍历
            System.out.println("foreach：" + arrayList);
        }
        Iterator iterator = arrayLists.iterator();                      // 遍历二：迭代器：Iterator
        while (iterator.hasNext()) {
            System.out.println("迭代器：" + iterator.next());
        }
        Object[] array = arrayLists.toArray();                          //遍历三：toArray()
        for (int i = 0; i < array.length; i++) {
            System.out.println("toArray：" + array[i]);
        }

        //// 【5】、Map分类：kay是不可重复
        Hashtable hashtable = new Hashtable();                      // 基于哈希表（散列表），是旧版的。线程安全的。key,value都不允许为null。
        HashMap hashMap = new HashMap();                            // 基于哈希表（散列表），相新版的。线程不安全。key,value允许为null。底层的链表是单向的。
        LinkedHashMap linkedHashMap = new LinkedHashMap();          // HashMap的子类，维护了添加的顺序，添加和删除时比效率就更低了。LinkedHashMap底层的链表是双向的。
        TreeMap treeMap = new TreeMap();                            // 按照key的大小顺序排列，要求元素要么实现Comparable接口，要么传入Comparator比较器规则（其他四个Map类kay不能重复是因为has值和equals，这个是因为compareto和compare）。
        Properties properties = new Properties();                   // Hashtable的子类，它的key和value的类型都是String类型，一般用来存储属性配置信息。对象.setProperty添加数据，对象.getProperty得到数据，是自己的方法

        //// 【6】、Map常用方法
        hashMap.put(1, "a");                                         // 添加一对映射关系到当前map集合中
        hashMap.putAll(hashtable);                                  // 把集合的所有映射关系添加到当前map中
        hashMap.remove(1);                                     // 根据key移除一对映射关系，并返回刚删除的value值，可接收可不接收
        hashMap.clear();                                             // 清空所有
        hashMap.containsKey(2);                                      // 是否包含某个key
        hashMap.containsValue("b");                                 // 是否包含某个value
        hashMap.get(1);                                              // 根据key获取value
        hashMap.size();                                              // 获取有效元素个数
        hashMap.isEmpty();                                           // 是否是空集合
        hashMap.keySet();                                            // 获取所有key返回到Set集合中
        hashMap.values();                                            // 获取所有value返回Collection集合
        hashMap.entrySet();                                          // 获取所有键值对返回到set集合
        properties.load(ClassLoader.getSystemResourceAsStream("condition.properties"));    // Properties独有  // 加载配置文件
        properties.setProperty("ip", "192.168.1.101");             // Properties独有  // 配置文件增加一个键值对
        properties.getProperty("ip", "无");      // Properties独有  // 获取key的value值，若没有给默认值“无”

        //// 【7】、Map集合的遍历
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println("Key:" + entry.getKey() + " Value:" + entry.getValue());
        }
        entries.forEach(entrie -> System.out.println(entrie));    // Lambda表达式的遍历
    }
}
