package rh.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.*;
import rh.utils.GetItems;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 读取点表数据，写入kafka
 */

public class UtgardTutorial1 {
    static Object value = "";
    static Properties props = null;
    static KafkaProducer<String, String> producer = null;
    static SimpleDateFormat sdf = null;
    static Map<String, String> clph = null;
    // 连接信息
    static ConnectionInformation ci = null;
    //所有点表名
    static List<String> allItems = null;

    static {
        try {
            props = new Properties();
            props.put("bootstrap.servers", "10.238.255.151:9092,10.238.255.152:9092,10.238.255.153:9092");
            props.put("group.id", "test");
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

            producer = new KafkaProducer<String, String>(props);

            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            ci = new ConnectionInformation();

            ci.setHost("localhost");         // 电脑IP
            ci.setDomain("");                  // 域，为空就行
            ci.setUser("administrator");             // 电脑上自己建好的用户名
            ci.setPassword("ts.1234");          // 用户名的密码

            ci.setClsid("04524449-C6B2-4d62-8471-C64FA1DDF64F");


            allItems = GetItems.dumpFlat();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readData() throws Exception {
        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();

        TimerTask timerTask = new TimerTask(4000); // 任务需要 4000 ms 才能执行完毕

//        System.out.printf("起始时间：%s\n\n", new SimpleDateFormat("HH:mm:ss").format(new Date()));

        // 延时 1 秒后，按 1 h的周期执行任务
        timer.scheduleAtFixedRate(timerTask, 1000, 3000 * 60 * 20, TimeUnit.MILLISECONDS);


        // 启动服务
        final Server server = new Server(ci, timer);
        try {
            // 连接到服务
            server.connect();
            // add sync access, poll every 500 ms，启动一个同步的access用来读取地址上的值，线程池每500ms读值一次
            // 这个是用来循环读值的，只读一次值不用这样
            final AccessBase access = new SyncAccess(server, 500);

            for (String str : allItems) {
                access.addItem(str, new DataCallback() {
                    public void changed(Item item, ItemState itemState) {

                        try {
                            if (itemState.getValue().getType() == 8) {
                                value = itemState.getValue().getObjectAsString().getString();
                            } else {
                                value = itemState.getValue().getObject();
                            }


                            if (item.getId().contains("DA.")) {
                                if (value.toString() == "true") {
                                    producer.send(new ProducerRecord<String, String>("test", sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId().split("\\.")[1] + "---" + "1" + "---" + (itemState.getQuality() == 192 ? "Good" : "Bad") + "---" + sdf.format(new Date())));
                                } else if (value.toString() == "false") {
                                    producer.send(new ProducerRecord<String, String>("test", sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId().split("\\.")[1] + "---" + "0" + "---" + (itemState.getQuality() == 192 ? "Good" : "Bad") + "---" + sdf.format(new Date())));
                                } else {
                                    producer.send(new ProducerRecord<String, String>("test", sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId().split("\\.")[1] + "---" + value + "---" + (itemState.getQuality() == 192 ? "Good" : "Bad") + "---" + sdf.format(new Date())));
                                }
                            } else {
                                if (value.toString() == "true") {
                                    producer.send(new ProducerRecord<String, String>("test", sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId() + "---" + "1" + "---" + (itemState.getQuality() == 192 ? "Good" : "Bad") + "---" + sdf.format(new Date())));
                                } else if (value.toString() == "false") {
                                    producer.send(new ProducerRecord<String, String>("test", sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId() + "---" + "0" + "---" + (itemState.getQuality() == 192 ? "Good" : "Bad") + "---" + sdf.format(new Date())));
                                } else {
                                    producer.send(new ProducerRecord<String, String>("test", sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId() + "---" + value + "---" + (itemState.getQuality() == 192 ? "Good" : "Bad") + "---" + sdf.format(new Date())));
                                }
                            }
                            // bw1.write(sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId() + "---" + value  + "---" + itemState.getQuality()  + "---" + sdf.format(new Date()) + "\r\n");

//                            System.out.println("value:" + value);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            // start reading，开始读值
            access.bind();

            while (true) {
                Thread.sleep(1000);
            }
            // stop reading，停止读取
            //access.unbind();
        } catch (final JIException e) {
            System.out.println(String.format("%08X: %s", e.getErrorCode(), server.getErrorMessage(e.getErrorCode())));
        } finally {
            producer.close();
        }
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

                //获取所有点表名
                GetItems.dumpFlat();
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

//            System.out.println("任务结束，当前时间：" + dateFormat.format(new Date()));
//            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception {
        readData();
    }
}