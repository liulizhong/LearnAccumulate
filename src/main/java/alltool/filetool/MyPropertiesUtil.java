package alltool.filetool;

import lombok.val;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author lizhong.liu
 * @version TODO
 * @class 读取配置文件工具类
 * @CalssName MyPropertiesUtil
 * @create 2020-12-23 16:13
 * @Des TODO
 */
public class MyPropertiesUtil {
    public static String load(String propertiesName, String key) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(propertiesName));
        String strValue = properties.getProperty(key);
        return strValue;
    }

    public static void main(String[] args) throws IOException {
        String str = MyPropertiesUtil.load("D:\\workhouse\\LearnAccumulate\\src\\main\\resources\\condition.properties", "condition.params.json");
        System.out.println(str);
    }
}
