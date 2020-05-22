package rh.influxdbconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
public class AllConsumerTo245File {
    /**
     * 消费所有数据到245服务器的Influxdb
     */
    static Properties properties = null;
    static KafkaConsumer<String, String> kafkaConsumer = null;

    static {
        //kafka配置
        properties = new Properties();
        properties.put("bootstrap.servers", "10.8.0.6:19092,10.8.0.6:19093,10.8.0.6:19094");
        properties.put("group.id", "all-245influxdb");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaConsumer = new KafkaConsumer<String,String>(properties);
    }

    public static void main(String[] args) throws Exception{
        SumConsumer();
    }

    /**
     * 全部数据
     */
    public static void SumConsumer() throws Exception{
        File file;
        File fileParents;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        kafkaConsumer.subscribe(Arrays.asList("test"));
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                file = new File("\\home\\liulizhong\\module\\influxdb\\kafkaopc" +
                        dateFormat.format(new Date()).substring(0,7) +
                        "\\" + dateFormat.format(new Date()).substring(0,10) + ".txt");
                fileParents = new File("\\home\\liulizhong\\module\\influxdb\\kafkaopc" + dateFormat.format(new Date()).substring(0,7));
                //System.out.println(file.getAbsolutePath());
                if(!fileParents.exists()){
                    fileParents.mkdirs();
                }
                if(!file.exists()){
                    file.createNewFile();
                }

                BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
                bw.write(record.value() + "\r\n");
                bw.flush();
                System.out.printf("test,offset = %d, value = %s", record.offset(), record.value());
                System.out.println();
            }
        }
    }
}
