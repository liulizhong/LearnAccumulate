package rhkafka.consumer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * 消费全部数据
 */
public class SumConsumer {
    static Properties properties = null;
    static KafkaConsumer<String, String> kafkaConsumer = null;
    static Date date = null;
    static SimpleDateFormat sdf = null;

    static {
        properties = new Properties();
        properties.put("bootstrap.servers", "10.238.255.151:9092,10.238.255.152:9092,10.238.255.153:9092");
        properties.put("group.id", "test");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaConsumer = new KafkaConsumer<String,String>(properties);

        date = new Date();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 全部数据
     */
    public static void SumConsumer() throws Exception{
        kafkaConsumer.subscribe(Arrays.asList("test"));
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                File file = new File("E:\\opcData\\" + sdf.format(new Date()).substring(0,7) + "\\" + sdf.format(new Date()).substring(0,10) + ".txt");
                File fileParents = new File("E:\\opcData\\" + sdf.format(new Date()).substring(0,7));
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

    public static void main(String[] args)throws Exception {
        SumConsumer();
    }
}