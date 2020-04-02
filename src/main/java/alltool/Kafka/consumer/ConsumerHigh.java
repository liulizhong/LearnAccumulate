package alltool.Kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author lizhong.liu
 * @version TODO
 * @class ??
 * @CalssName ConsumerHigh
 * @create 2020-04-01 13:39
 * @Des TODO
 */
public class ConsumerHigh {
    public static void main(String[] args) {
        Properties pro = new Properties();
        try {
            pro.load(new FileInputStream(new File("src\\main\\java\\alltool\\Kafka\\consumer\\consumer.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(pro);
        //发布订阅主题(可多主题)
        consumer.subscribe(Arrays.asList("rangetest"));
        while (true) {
            //消费数据
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                //获取主题、偏移量、分区、key、value
                String topic = record.topic();
                long offset = record.offset();
                String key = record.key();
                String value = record.value();
                int partition = record.partition();
                System.out.println("topic:" + topic + "\tpartition:" + partition + "\toffset:" + offset + "\tkey:" + key + "\tvalue:" + value);
            }
        }
    }
}
