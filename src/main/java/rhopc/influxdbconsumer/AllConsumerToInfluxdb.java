package rhopc.influxdbconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author lizhong.liu
 * @version TODO
 * @class 所有数据直接实时写入Influxdb
 * @CalssName AllConsumerToInfluxdb
 * @create 2020-05-25 13:36
 * @Des TODO
 */
public class AllConsumerToInfluxdb {
    /**
     * 消费所有数据到245服务器磁盘
     */
    static InfluxDB influxDB = null;
    static Properties properties = null;
    static KafkaConsumer<String, String> kafkaConsumer = null;

    static {

        //kafka配置
        properties = new Properties();
//        properties.put("bootstrap.servers", "10.8.0.6:9093,10.8.0.6:9094,10.8.0.6:9095");
//        properties.put("bootstrap.servers", "10.238.255.175:19092,10.238.255.175:19093,10.238.255.175:19094");
        properties.put("bootstrap.servers", "10.238.255.151:9092,10.238.255.152:9092,10.238.255.153:9092");
        properties.put("group.id", "alltoinfluxdb");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaConsumer = new KafkaConsumer<String, String>(properties);

        //influxdb链接初始化
        influxDB = InfluxDBFactory.connect("http://192.168.1.245:8086", "administrator", "abc@123@!@#");
    }


    public static void main(String[] args) throws Exception {
        SumConsumer();
    }


    /**
     * 全部数据
     */
    public static void SumConsumer() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int num = 0;
        long start = System.currentTimeMillis();
        //订阅opc点表Kafka-opc主题
        kafkaConsumer.subscribe(Arrays.asList("test"));
//        System.out.println("influxDB:" + influxDB + "====" + "kafkaConsumer:" + kafkaConsumer);
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                String[] split_record = record.value().split("---");
                influxDB.setDatabase("tashanopc");
                influxDB.write(Point.measurement("opcall")
                        .time(dateFormat.parse(split_record[4]).getTime(), TimeUnit.MILLISECONDS)
                        .tag("opcname", split_record[1])
                        .addField("status", split_record[3])
                        .addField("equipmenttime", split_record[0])
                        .addField("opcvalue", split_record[2])
                        .build());
                System.out.println((System.currentTimeMillis() - start) / 1000 + "秒：" + num++);
            }
        }
    }
}