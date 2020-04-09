package rhkafka.utils;

import oracle.jdbc.driver.OracleDriver;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 获取各系统的所有点表名
 */
public class GetNames {
    static Connection connect = null;
    static Statement statement = null;
    static ResultSet resultSet = null;
    static String name = "";
    static Map<String,String> map= new HashMap<String,String>();

    /**
     * 建立连接
     */
    static {
        try {
            Properties pro = new Properties();
            pro.put("user", "clph");
            pro.put("password", "clph");

            Driver driver = new OracleDriver();
            DriverManager.deregisterDriver(driver);

            connect = driver.connect("jdbc:oracle:thin:@10.238.255.252:1521:orcl", pro);;
            statement = connect.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //产量平衡
    public static Map<String,String> clph() throws Exception{
        //执行sql语句
        resultSet = statement.executeQuery("select distinct ATTRIBUTENAME from tb_beltattribute");
        //处理结果集
        while (resultSet.next())
        {
            name = resultSet.getString("ATTRIBUTENAME");
            map.put(name,"clph");
            //System.out.println("点表名称："+name);  //打印输出结果集
        }
        close();
        return map;
    }

    /**
     * 释放资源
     * @throws Exception
     */
    public static void close() throws Exception{
        if (resultSet!=null) resultSet.close();
        if (statement!=null) statement.close();
        if (connect!=null) connect.close();
    }
}
