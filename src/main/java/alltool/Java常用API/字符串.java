package alltool.Java常用API;

/**
 * @author lizhong.liu
 * @version TODO
 * @class ??
 * @CalssName 字符穿
 * @create 2021-03-04 11:29
 * @Des TODO
 */
public class 字符串 {
    public static void main(String[] args) {
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
        System.out.println(str1 == str2); 				//true		一个对象 [在常量池]
        System.out.println(str1 == str3); 				// false     str指向堆中对象两个对象
        System.out.println(str3 == str4); 				// false
        System.out.println(str5 == str6); 				// true    常量 + 常量=》常量池
        System.out.println(str5 == str7); 				// false   变量 + 变量=》堆中
        System.out.println(str5 == str8); 				// false   常量 + 变量=》堆中


    }
}
