package rh.consumer;

import rh.utils.GetNames;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import redis.clients.jedis.Jedis;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class ClphConsumer2 {
    /**
     * 消费clph的数据
     */
    static Properties properties = null;
    static KafkaConsumer<String, String> kafkaConsumer = null;
    static Jedis jedis = null;
    static Map<String, String> clph = null;

    static {
        //kafka配置
        properties = new Properties();
        properties.put("bootstrap.servers", "10.238.255.151:9092,10.238.255.152:9092,10.238.255.153:9092");
        properties.put("group.id", "test-266");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaConsumer = new KafkaConsumer<String, String>(properties);
/*
        //redis配置
        jedis = new Jedis("10.238.255.198");
        jedis.select(2);*/

/*        try {
            //获取产量平衡所有点表名    <点表名,clph>
            clph = GetNames.clph();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    /**
     * 产量平衡
     */
    public static void clphConsumer() throws Exception {
        int num = 0;
        long start = System.currentTimeMillis();
//        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();

//        TimerTask timerTask = new TimerTask(4000); // 任务需要 4000 ms 才能执行完毕

//        System.out.printf("起始时间：%s\n\n", new SimpleDateFormat("HH:mm:ss").format(new Date()));

        // 延时 1 秒后，按 1 h的周期执行任务
//        timer.scheduleAtFixedRate(timerTask, 1000, 3000 * 60 * 20, TimeUnit.MILLISECONDS);

        //消费test主题数据
        kafkaConsumer.subscribe(Arrays.asList("test"));
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {

                //clph,offset = 12641951, value = 2020/03/30 16:43:24---DA.CST1070BAI0302---184---192
//                判断该条数据是否为产量平衡的数据，是：则写入redis
//                if (clph.get(record.value().split("---")[1]) == "clph") {
//                    jedis.hset("clph:realTimeData",record.value().split("---")[1],record.value());
//                System.out.printf("test,offset = %d, value = %s", record.offset(), record.value());
//                System.out.println();
                System.out.println((System.currentTimeMillis()-start)/1000+":"+num++);
//                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        clphConsumer();
    }

    private static class TimerTask implements Runnable {

        private final int sleepTime;
        private final SimpleDateFormat dateFormat;

        public TimerTask(int sleepTime) {
            this.sleepTime = sleepTime;
            dateFormat = new SimpleDateFormat("HH:mm:ss");
        }


        public void run() {
//            System.out.println("任务开始，当前时间：" + dateFormat.format(new Date()));

            try {
//                System.out.println("模拟任务运行...");
                Thread.sleep(sleepTime);
                //获取产量平衡点表名
                GetNames.clph();
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

//            System.out.println("任务结束，当前时间：" + dateFormat.format(new Date()));
//            System.out.println();
        }

    }


    /**
     * 全部数据
     */
    public static void SumConsumer() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        kafkaConsumer.subscribe(Arrays.asList("test"));
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                File file = new File("E:\\opcData\\" + dateFormat.format(new Date()).substring(0, 7) + "\\" + dateFormat.format(new Date()).substring(0, 10) + ".txt");
                File fileParents = new File("E:\\opcData\\" + dateFormat.format(new Date()).substring(0, 7));
                //System.out.println(file.getAbsolutePath());
                if (!fileParents.exists()) {
                    fileParents.mkdirs();
                }
                if (!file.exists()) {
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
