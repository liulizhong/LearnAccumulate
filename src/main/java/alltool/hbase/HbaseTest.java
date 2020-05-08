package alltool.hbase;

import java.io.IOException;

/**
 * @author lizhong.liu
 * @version TODO
 * @class ??
 * @CalssName HbaseTest
 * @create 2020-05-07 17:52
 * @Des TODO
 */
public class HbaseTest {
    public static void main(String[] args) throws IOException {
        System.out.println(HbaseAPI.isTableExist("testNamel"));
    }
}
