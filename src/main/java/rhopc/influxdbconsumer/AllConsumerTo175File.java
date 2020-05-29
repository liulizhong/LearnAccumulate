package rhopc.influxdbconsumer;

import com.jcraft.jsch.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

/**
 * @author lizhong.liu
 * @version TODO
 * @class 所有opc点表数据写入服务器磁盘
 * @CalssName AllConsumerTo245File
 * @create 2020-05-22 10:24
 * @Des TODO
 */
public class AllConsumerTo175File {
    /**
     * 消费所有数据到245服务器的Influxdb
     */
    static String basePath = "/home/liulizhong/module/influxdb/kafkaopc/";
    static Properties properties = null;
    static KafkaConsumer<String, String> kafkaConsumer = null;
    static JSch jsch = null;
    static Session session = null;
    static Channel channel = null;
    static ChannelSftp sftp = null;

    static {
        System.out.println("一：session:" + session + ", channel:" + channel + ", sftp:" + sftp);
        //【一】、kafka配置
        properties = new Properties();
//        properties.put("bootstrap.servers", "10.8.0.6:9093,10.8.0.6:9094,10.8.0.6:9095");
        properties.put("bootstrap.servers", "10.238.255.151:9092,10.238.255.152:9092,10.238.255.153:9092");
        properties.put("group.id", "allto245file");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaConsumer = new KafkaConsumer<String, String>(properties);

        //【二】、服务器连接
        jsch = new JSch();
        try {
            session = jsch.getSession("root", "192.168.1.244", 22);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        // 设置登陆主机的密码
        session.setPassword("abc@123@!@#");// 设置密码
        // 设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        // 设置登陆超时时间
        try {
            session.connect(300000);
            channel = (Channel) session.openChannel("sftp");
            channel.connect(10000000);
            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            e.printStackTrace();
        }
        System.out.println("二：session:" + session + ", channel:" + channel + ", sftp:" + sftp);
    }

    public static void main(String[] args) throws Exception {
        SumConsumer();
    }

    /**
     * 全部数据
     */
    public static void SumConsumer() throws Exception {
        String filePash = "";
        String parentsfilePash = "";
        File file;
        File fileParents;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        kafkaConsumer.subscribe(Arrays.asList("test"));
        System.out.println("kafkaConsumer:" + kafkaConsumer);
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                //1、每次循环创建新的文件夹判断
                filePash = basePath + dateFormat.format(new Date()).substring(0, 7) + "/" + dateFormat.format(new Date()).substring(0, 10) + ".txt";
                parentsfilePash = basePath + dateFormat.format(new Date()).substring(0, 7);
                sftp.cd(basePath + dateFormat.format(new Date()).substring(0, 7) + "/");
                //判断父文件夹是否存在，不存在即创建
                SftpATTRS attrs = null;
                try {
                    attrs = sftp.stat(parentsfilePash);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                if (attrs == null) {
                    sftp.mkdir(parentsfilePash);
                }
                //文件输出
                OutputStream o = null;
                file = new File(filePash);
                o = sftp.put(file.getName(), ChannelSftp.APPEND);
                String[] split_record = record.value().split("---");
                String recordString = "opcall,opcname=" + split_record[1] + " status=" + split_record[3] + ",equipmenttime=" + split_record[0] + ",opcvalue=" + split_record[2] + " " + dateFormat.parse(split_record[4]).getTime();
                o.write(recordString.getBytes("UTF-8"));
                o.close();
            }
        }
    }
}