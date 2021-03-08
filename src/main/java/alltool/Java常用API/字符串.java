package alltool.Java常用API;

import java.io.UnsupportedEncodingException;

/**
 * @author lizhong.liu
 * @version TODO
 * @class ??
 * @CalssName 字符穿
 * @create 2021-03-04 11:29
 * @Des TODO
 */
public class 字符串 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        //// 【1】、创建和比较
        String str1 = "1";   // 字符串常量是存储在字符串常量池中。凡是new出来在堆中。
        String str2 = "1";
        String str3 = new String("1");
        String str4 = new String("1");
        String str5 = "11";
        String str6 = "1" + "1";
        String str7 = str1 + str2;
        String str8 = str1 + "1";
        final String str9 = "1";
        final String str10 = "1";
        String str11 = str9 + str10;
        String str12 = new String("1") + new String("1");//指向堆中的常量
        String str13 = (new String("1") + new String("1")).intern();
        System.out.println(str1 == str2); 				//true		一个对象 [在常量池]
        System.out.println(str1 == str3); 				// false     str指向堆中对象两个对象
        System.out.println(str3 == str4); 				// false
        System.out.println(str5 == str6); 				// true    常量 + 常量=》常量池
        System.out.println(str5 == str7); 				// false   变量 + 变量=》堆中
        System.out.println(str5 == str8); 				// false   常量 + 变量=》堆中
        System.out.println(str5 == str11); 				// false   常量 + 变量=》堆中  // 加final都是在常量池中，只是指向常量池的常量和常量结果相同（str1、str2、"1"都是常量）
        System.out.println(str5 == str12); 				// false   指向堆中的常量和字符串常量池不同
        System.out.println(str5 == str13); 				// true   String方法(s1 + s2).intern();	是将结果储存到字符串常量池

        //// 【2】、String的常用方法
        "abcd".length();                                         // 求字符串的长度  //String s = null; s.length();//空指针异常
        " ab cd ".trim();                                        // 去掉前后的所有空格（包括Tab键空白符），中间的不会去掉，后期常用
        "abcd".equals(" abcd");                                 // 比较字符串的字符内容，严格区分大小写，==是比较对象地址
        "abcd".equalsIgnoreCase("AbcD");         //比较字符串的字符内容，忽大小写
        "abcd".isEmpty();                                        //是否是空字符串
        "".equals("abcd");  "abcd".isEmpty();	                  //  通"abcd" == null;   第一个方法最安全，因为不会报空指针异常，要是能确认非空就都可以用
        "abcd".toUpperCase();                                    // 转大写，拿一个变量接收一下
        "abcd".toLowerCase();                                    //转小写

        //// 【3】、字节、字符、字符串  的转化
        "ab".concat("cd");                                      // 通"abcd"+"abcd"; 拼接字符串，小心空指针异常
        char[] arr = "abcd".toCharArray();                      // 把字符串转成字符数组
        char c = "abcd".charAt(2);                              // 获取字符串指定下标 的‘字符’
        String s = new String(arr, 1, 2);        // 将字符数组arr中索引2~3的元素生成新字符串
        byte[] utf8s = "abcd".getBytes("utf8");  // 字符串转化为字节数组
        String str = new String(utf8s);                           // 字节数组转化成字符串

        //// 【4】、字符穿的开头、结尾、查找、截取、正则、替换等
        "abcd".endsWith("d");                                   // 字符串是否以指定的后缀结束。
        "abcd".startsWith("b", 1);              // 字符串从指定索引开始的子字符串是否以指定前缀开始。
        "abcd".contains("bc");                                  // 字符串包含指定的 char 值序列时，返回 true。
        int cha1 = "abcd".indexOf('b', 3);     // 返回在此字符串中第一次出现指定字符处的索引，从指定的索引开始搜索。未找到返回-1
        int cha2 = "abcd".lastIndexOf('b', 3); // 返回指定子字符串在此字符串中最后一次出现处的索引，从指定的索引开始反向搜索。未找到返回-1
        String substring = "abcdef".substring(2, 4);             // 返回一个新字符串，它是此字符串从beginIndex开始截取到endIndex(不包含)的一个子字符串。
        boolean matches = "abcd".matches("\\d+");       // 告知此字符串是否匹配给定的正则表达式。
        "abcd".replaceAll("ab", "b");      // 返回替换后的子串，全部替换，支持正则
        "abcd".replaceFirst("ab", "b");    // 返回替换后的子串，首个替换，支持正则
        "a:b:cd:ef".split(":",3);                 // 根据匹配给定的正则表达式来拆分此字符串，最多不超过limit个，如果超过了，剩下的全部都放到最后一个元素中。
        /*   正则表达式
                "\\d"		        表示一个数字
                "\\d+"		        表示1-n个数字，其他类型也一样，有+号表示后边连续多个本成员
                "\\D"		        表示一个非数字
                "137|135\\d{8}"	    表示137或者135开头后边8个数字
                "."			        表示任意字符
                "\\s"		        表示一个空白字符，多个就加+
                "\\S"		        表示一个非空白字符
                "^"			        表示开头
                "$"			        表示结尾
                "\\w"		        表示一个单词字符，包括26个英文字母大小写下划线和0-9
                "\\W"		        表示非单词字符       */

        //// 【5】、可变字符串
        StringBuffer sf = new StringBuffer();                             // 线程安全的，效率较低（但远比string拼接效率高得多）
        StringBuilder sb = new StringBuilder();                           // 线程不安全，效率较高（但远比string拼接效率高得多）对于单线程来说，建议使用它
        sf.append("hello").append("world").append("java");
        sb.append("hello world java!").delete(2,4).deleteCharAt(2)     // 删除下标[2, 4)的元素，再删除下标是2的元素，此时 sb="he world java!"
               .insert(2,"llo")                             // 下标2的元素位置插入 "llo" ，此时还原了
               .replace(7,10,"orl").reverse()          // 下标[7, 10)的元素替换为"llo"，并反转
                .setCharAt(8,'？');                         // 下标[2, 4)的字符替换为‘？’
    }
}
