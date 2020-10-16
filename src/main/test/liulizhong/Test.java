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
        System.out.println("开始");
        for (int i = 0; i < 100; i++) {
            if (i == 10) {
                break;
            }
            System.out.println(i);
        }
        System.out.println("出来了！！！");
    }
}
