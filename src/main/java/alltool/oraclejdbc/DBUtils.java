package alltool.oraclejdbc;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author lizhong.liu
 * @version TODO
 * @CalssName TestDBUtils
 * @create 2020-03-25 15:12
 * @Des TODO
 */
public class DBUtils {

    static DataSource ds;

    static {
        Properties pro = new Properties();
        try {
            pro.load(new FileInputStream(new File("src\\main\\java\\alltool\\oraclejdbc\\druid.properties")));
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static QueryRunner qr = new QueryRunner(ds);

}