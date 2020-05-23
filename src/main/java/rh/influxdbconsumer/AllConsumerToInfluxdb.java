
package rh.influxdbconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


/**
 * @author lizhong.liu
 * @version TODO
 * @class 所有数据直接实时写入Influxdb
 * @CalssName AllConsumerToInfluxdb222
 * @create 2020-05-22 10:25
 * @Des TODO
 */

public class AllConsumerToInfluxdb {

/**
     * 消费所有数据到245服务器磁盘
     */


    static InfluxDB influxDB;
    static Properties properties = null;
    static KafkaConsumer<String, String> kafkaConsumer = null;
    static Jedis jedis = null;
    static Map<String, String> clph = null;

    static {
        //kafka配置
        properties = new Properties();
        properties.put("bootstrap.servers", "10.238.255.151:9092,10.238.255.152:9092,10.238.255.153:9092");
        properties.put("group.id", "all-245file");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaConsumer = new KafkaConsumer<String,String>(properties);

        //influxdb链接初始化
        influxDB = InfluxDBFactory.connect("http://192.168.1.245:8086","administrator","abc@123@!@#");
    }


    public static void main(String[] args) throws Exception{
        SumConsumer();
    }



/**
     * 全部数据
     */

    public static void SumConsumer() throws Exception{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //订阅opc点表Kafka主题
        kafkaConsumer.subscribe(Arrays.asList("test"));
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
//                String s = record.value().split("---")[1];
                influxDB.setDatabase("tasahnopc");
                influxDB.write(Point.measurement("opcall")
                        .time(dateFormat.parse(record.value().split("---")[4]).getTime(), TimeUnit.MILLISECONDS)
                        .addField("opcname", record.value().split("---")[1])
                        .addField("status",  record.value().split("---")[3])
                        .addField("equipmenttime", record.value().split("---")[0])
                        .addField("opcvalue", record.value().split("---")[2])
                        .build());
                System.out.printf("test,offset = %d, value = %s", record.offset(), record.value());
                System.out.println();
            }
        }
    }
}

