package alltool.Java常用API;


/*
包装类：java.lang
 byte -> Byte
 short -> Short
 int  -> Integer
 long -> Long
 float -> Float
 double -> Double
 char -> Character
 boolean -> Boolean
 */

public class 包装类 {
    public static void main(String[] args) {

        //// 【1】、装箱vs拆箱
        int b = 20;
        Integer i2 = b;  // 自动装箱
        int b3 = i2;    // 自动拆箱

        //// 【2】、字符穿vs数字 的互相转化
        int num1 = new Integer("123");  // 字符串转化为数字
        int num2 = Integer.parseInt("123");  // 字符串转化为数字
        int num3 = Integer.valueOf("123");    // 字符串转化为数字
        String str1 = String.valueOf(1234);       // 数字转化为字符串
        System.out.println(Integer.MIN_VALUE);  //  打印int类型最大值

        //// 【3】、Integer转化为 二进制
        System.out.println(Integer.toHexString(125));    //十进制转成十六进制
        System.out.println(Integer.toOctalString(125));    //转成八进制
        System.out.println(Integer.toBinaryString(125));    //转成二进制
        System.out.println(0x1100100);                          //0X  零X/x  十六进制 按照 十进制 打印

        //// 【4】、Char转换大小写
        System.out.println(Character.toUpperCase('b'));  // 转成大写

        //// 【5】、缓存问题 Byte,Short,Integer,Long：-128~127  Character:0~127   Boolean：true,false   float,double：无任何缓冲对象
        int a1 = 128;
        int a2 = 128;
        int b1 = 1;
        int b2 = 1;
        Integer t1 = 128;//自动装箱
        Integer t2 = 128;
        Integer t3 = 1;
        Integer t4 = 1;
        Integer t5 = new Integer(1);
        Integer t6 = new Integer(1);
        System.out.println(a1 == a2);     //true  比较的是值
        System.out.println(b1 == b2);     //true  比较的是值
        System.out.println(t1 == t2);     //false  因为比较的是对象的地址
        System.out.println(t1 == t2);     //false  证数缓存范围内的对象属于同一对象
        System.out.println(t5 == t6);     //false  虽然在缓存内，但new的对象就是两个
    }
}
