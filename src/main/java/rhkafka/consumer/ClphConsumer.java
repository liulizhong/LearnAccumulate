package rhkafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;
import redis.clients.jedis.Jedis;

/**
 * 消费clph的数据
 */
public class ClphConsumer {
    static Properties properties = null;
    static KafkaConsumer<String, String> kafkaConsumer = null;
    static Jedis jedis = null;

    static {
        properties = new Properties();
        properties.put("bootstrap.servers", "10.238.255.151:9092,10.238.255.152:9092,10.238.255.153:9092");
        properties.put("group.id", "test");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaConsumer = new KafkaConsumer<String,String>(properties);

        jedis = new Jedis("10.238.255.198");
        jedis.select(2);
    }

    /**
     * 产量平衡
     */
    public static void CLPHConsumer(){
        kafkaConsumer.subscribe(Arrays.asList("clph"));
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                //clph,offset = 12641951, value = 2020/03/30 16:43:24---DA.CST1070BAI0302---184---192
                jedis.hset("clph",record.value().split("---")[1],record.value());
                System.out.printf("clph,offset = %d, value = %s", record.offset(), record.value());
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        CLPHConsumer();
    }
}