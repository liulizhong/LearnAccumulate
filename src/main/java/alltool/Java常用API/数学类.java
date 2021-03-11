package alltool.Java常用API;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author lizhong.liu
 * @version TODO
 * @class ??
 * @CalssName 数学类
 * @create 2021-03-08 16:42
 * @Des TODO
 */
public class 数学类 {
    public static void main(String[] args) {

        //// 【1】、数学类常用基本方法
        Math.abs(-12);                     // 绝对值
        double pi = Math.PI;              // 常量Π3.14159267.。。。。
        Math.acos(20.2);                   // asin,atan,cos,sin,tan  角函数
        Math.sqrt(16.0);                   // 平方根
        Math.pow(2,3);                     // 3的2次幂
        Math.log(10);                      // 自然对数
        Math.exp(10);                      // e为底指数
        Math.max(2,3);                     // 取最大值，最小值Math.min(double a,double b)
        Math.random();                     // 返回0.0到1.0的随机数
        Math.round(12.5);                  // double型数据a转换为long型（四舍五入）
        Math.floor(12.5);                  // 往小舍去小数点,返回double
        Math.ceil(12.1);                   // 往大进一,返回double
        Math.toDegrees(12.5);              // 弧度—>角度   还有角度—>弧度：Math.toRadians(12.5);

        //// 【2】、比 int 最大值还要大的  BigInteger  无精度限制
        BigInteger bigInteger = new BigInteger("12345678900000000000");   // 创建新的BigInteger对象
        BigDecimal bigDecimal = new BigDecimal("12345678912345678900");   // BigDecimal 的方法和 BigInteger的方法一样，不举例了
        BigInteger add = bigInteger.add(bigInteger);                               //  返回其值为 (this + val) 的 BigInteger。
        BigInteger subtract = bigInteger.subtract(bigInteger);                     // 返回其值为 (this - val) 的 BigInteger。
        BigInteger multiply = bigInteger.multiply(bigInteger);                     // 返回其值为 (this * val) 的 BigInteger。
        BigInteger divide = bigInteger.divide(bigInteger);                         // 返回其值为 (this / val) 的 BigInteger。整数相除只保留整数部分。
        BigInteger remainder = bigInteger.remainder(bigInteger);                   // 返回其值为 (this % val) 的 BigInteger。
        BigInteger pow = bigInteger.pow(2);                                        // 返回其值为 (thisexponent) 的 BigInteger。
    }
}
