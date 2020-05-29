package rhopc.producer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import rh.utils.GetItems;
import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.AccessBase;
import org.openscada.opc.lib.da.DataCallback;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.ItemState;
import org.openscada.opc.lib.da.Server;
import org.openscada.opc.lib.da.SyncAccess;


/**
 * 读取点表数据，写入kafka
 */

public class ReadOPCToLocalFile {
    static String basePath = "D:/opcDataliu/";
    static Object value = "";
    //    static Properties props = null;
//    static KafkaProducer<String, String> producer = null;
    static SimpleDateFormat sdf = null;
    // 连接信息
    static ConnectionInformation ci = null;
    //所有点表名
    static List<String> allItems = null;

    static {
        try {
//            props = new Properties();
//            props.put("bootstrap.servers", "10.238.255.151:9092,10.238.255.152:9092,10.238.255.153:9092");
//            props.put("group.id", "influxdb");
//            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//
//            producer = new KafkaProducer<String, String>(props);

            System.out.println("111111");
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            ci = new ConnectionInformation();

            ci.setHost("10.238.255.198");         // 电脑IP
            ci.setDomain("");                  // 域，为空就行
            ci.setUser("administrator");             // 电脑上自己建好的用户名
            ci.setPassword("ts.1234");          // 用户名的密码

            ci.setClsid("04524449-C6B2-4d62-8471-C64FA1DDF64F");

            System.out.println("222222");

            System.out.println(allItems);
            allItems = GetItems.dumpFlat();
            System.out.println(allItems);
            System.out.println("333333333");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readData() throws Exception {
        System.out.println("444444444444444");
        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();

        TimerTask timerTask = new TimerTask(4000); // 任务需要 4000 ms 才能执行完毕

//        System.out.printf("起始时间：%s\n\n", new SimpleDateFormat("HH:mm:ss").format(new Date()));

        // 延时 1 秒后，按 1 h的周期执行任务
        timer.scheduleAtFixedRate(timerTask, 1000, 3000 * 60 * 20, TimeUnit.MILLISECONDS);


        System.out.println("5555555555555555");
        // 启动服务
        final Server server = new Server(ci, timer);
        try {
            // 连接到服务
            server.connect();
            // add sync access, poll every 500 ms，启动一个同步的access用来读取地址上的值，线程池每500ms读值一次
            // 这个是用来循环读值的，只读一次值不用这样
            final AccessBase access = new SyncAccess(server, 500);

            System.out.println("666666666666666");
            for (String str : allItems) {
                System.out.println("7777777777777");
                access.addItem(str, new DataCallback() {
                    public void changed(Item item, ItemState itemState) {
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String filePash = basePath + dateFormat.format(new Date()).substring(0, 7) + "/" + dateFormat.format(new Date()).substring(0, 10) + ".txt";
                            String parentsfilePash = basePath + dateFormat.format(new Date()).substring(0, 7);
                            File file = new File(filePash);
                            File fileParents = new File(parentsfilePash);
                            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
                            String recordString = "";
                            if (!fileParents.exists()) {
                                fileParents.mkdirs();
                            }
                            if (!file.exists()) {
                                file.createNewFile();
                            }
                            if (itemState.getValue().getType() == 8) {
                                value = itemState.getValue().getObjectAsString().getString();
                            } else {
                                value = itemState.getValue().getObject();
                            }
                            if (item.getId().contains("DA.")) {
                                if (value.toString() == "true") {
//                                    producer.send(new ProducerRecord<String, String>("influxdb", sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId().split("\\.")[1] + "---" + "1" + "---" + (itemState.getQuality() == 192 ? "Good" : "Bad") + "---" + sdf.format(new Date())));
                                    recordString = "opcall,opcname=" + item.getId().split("\\.")[1] + " status=" + (itemState.getQuality() == 192 ? "Good" : "Bad") + ",equipmenttime=" + sdf.format(itemState.getTimestamp().getTime()).replaceAll(" ", "-") + ",opcvalue=" + "1" + " " + System.currentTimeMillis() + "\r\n";
                                } else if (value.toString() == "false") {
//                                    producer.send(new ProducerRecord<String, String>("influxdb", sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId().split("\\.")[1] + "---" + "0" + "---" + (itemState.getQuality() == 192 ? "Good" : "Bad") + "---" + sdf.format(new Date())));
                                    recordString = "opcall,opcname=" + item.getId().split("\\.")[1] + " status=" + (itemState.getQuality() == 192 ? "Good" : "Bad") + ",equipmenttime=" + sdf.format(itemState.getTimestamp().getTime()).replaceAll(" ", "-") + ",opcvalue=" + "0" + " " + System.currentTimeMillis() + "\r\n";
                                } else {
//                                    producer.send(new ProducerRecord<String, String>("influxdb", sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId().split("\\.")[1] + "---" + value + "---" + (itemState.getQuality() == 192 ? "Good" : "Bad") + "---" + sdf.format(new Date())));
                                    recordString = "opcall,opcname=" + item.getId().split("\\.")[1] + " status=" + (itemState.getQuality() == 192 ? "Good" : "Bad") + ",equipmenttime=" + sdf.format(itemState.getTimestamp().getTime()).replaceAll(" ", "-") + ",opcvalue=" + value + " " + System.currentTimeMillis() + "\r\n";
                                }
                            } else {
                                if (value.toString() == "true") {
//                                    producer.send(new ProducerRecord<String, String>("influxdb", sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId() + "---" + "1" + "---" + (itemState.getQuality() == 192 ? "Good" : "Bad") + "---" + sdf.format(new Date())));
                                    recordString = "opcall,opcname=" + item.getId() + " status=" + (itemState.getQuality() == 192 ? "Good" : "Bad") + ",equipmenttime=" + sdf.format(itemState.getTimestamp().getTime()).replaceAll(" ", "-") + ",opcvalue=" + "1" + " " + System.currentTimeMillis() + "\r\n";
                                } else if (value.toString() == "false") {
//                                    producer.send(new ProducerRecord<String, String>("influxdb", sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId() + "---" + "0" + "---" + (itemState.getQuality() == 192 ? "Good" : "Bad") + "---" + sdf.format(new Date())));
                                    recordString = "opcall,opcname=" + item.getId() + " status=" + (itemState.getQuality() == 192 ? "Good" : "Bad") + ",equipmenttime=" + sdf.format(itemState.getTimestamp().getTime()).replaceAll(" ", "-") + ",opcvalue=" + "0" + " " + System.currentTimeMillis() + "\r\n";
                                } else {
//                                    producer.send(new ProducerRecord<String, String>("influxdb", sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId() + "---" + value + "---" + (itemState.getQuality() == 192 ? "Good" : "Bad") + "---" + sdf.format(new Date())));
                                    recordString = "opcall,opcname=" + item.getId() + " status=" + (itemState.getQuality() == 192 ? "Good" : "Bad") + ",equipmenttime=" + sdf.format(itemState.getTimestamp().getTime()).replaceAll(" ", "-") + ",opcvalue=" + value + " " + System.currentTimeMillis() + "\r\n";
                                }
                            }
                            bw.write(recordString);
                            System.out.println(recordString);
                            bw.flush();
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

        }
    }

    /**
     * 多线程方式获取所有点表名的方法TimerTask(int sleepTime)
     *
     * @throws Exception
     */
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