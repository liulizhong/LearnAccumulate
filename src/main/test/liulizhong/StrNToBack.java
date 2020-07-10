package liulizhong;

import org.junit.Test;

/**
 * @author lizhong.liu
 * @version TODO
 * @class ??
 * @CalssName StrNToBack
 * @create 2020-07-10 11:24
 * @Des TODO
 */
public class StrNToBack {
    @Test
    public void test1() {
        String S =" abcXYZdef";
        String resultStr = sinistrogyration(S, 3);
        System.out.println(resultStr);
    }

    public String sinistrogyration(String str, int n) {
        String newStr = str.replaceAll(" ", "");
        return newStr.substring(n, newStr.length()) + newStr.substring(0, n);
    }
}
