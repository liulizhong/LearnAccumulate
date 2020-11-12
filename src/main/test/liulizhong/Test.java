package liulizhong;

import alltool.hbase.HbaseAPI;

import java.io.IOException;

/**
 * @author lizhong.liu
 * @version TODO
 * @class ??
 * @CalssName Test
 * @create 2020-07-30 10:13
 * @Des TODO
 */
public class Test {
    public static void main(String[] args) throws IOException {
        int i = 0;
        int j = 0;
        while (i < 10) {
            while (j <= 5) {
                if (i == 8) {
                    return;
                }
                if (j == 2) {
                    break;
                }
                j++;
            }
            i++;
        }
        System.out.println("外边代码执行了");
        System.out.println(i);
        System.out.println(j);
    }
}
