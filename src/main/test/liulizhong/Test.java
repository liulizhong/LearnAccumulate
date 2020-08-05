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
        System.out.println("测试成功！！！！！");
        System.out.println(HbaseAPI.isTableExist("bizhan:user"));
        System.out.println("测试成功！！！！！");
    }
}
