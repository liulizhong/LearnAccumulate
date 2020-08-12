//package alltool.filetool;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//import org.jinterop.dcom.common.JIException;
//import org.openscada.opc.lib.common.ConnectionInformation;
//import org.openscada.opc.lib.da.AccessBase;
//import org.openscada.opc.lib.da.DataCallback;
//import org.openscada.opc.lib.da.Item;
//import org.openscada.opc.lib.da.ItemState;
//import org.openscada.opc.lib.da.Server;
//import org.openscada.opc.lib.da.SyncAccess;
//
///**
// * @author lizhong.liu
// * @version TODO
// * @class 读取指定电脑OPC数据写入本地文件系统
// * @CalssName ReadOpcToFile
// * @create 2020-06-09 14:15
// * @Des TODO
// */
//public class ReadOpcToFile {
//    static Object value = "";           // 用于存储opc的value值
//    static SimpleDateFormat sdf = null;  // 有用到时间格式工具类
//    static Map<String, String> allOPC = new HashMap<String, String>();  //存储opc所有点表名及点表值
//    // 连接信息
//    static ConnectionInformation ci = null;    // 连接电脑的opc系统
//    static Long nums = 0l;                      // 记录数据条数
//    //所有点表名
//    static List<String> allItems = null;        // 临时存储所有点表名
//
//    static {
//        try {
//            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//            ci = new ConnectionInformation();
//            ci.setHost("10.238.255.198");         // 电脑IP
//            ci.setDomain("");                  // 域，为空就行
//            ci.setUser("administrator");             // 电脑上自己建好的用户名
//            ci.setPassword("ts.1234");          // 用户名的密码
//            ci.setClsid("04524449-C6B2-4d62-8471-C64FA1DDF64F");
//
//            allItems = dumpFlat();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void readData() throws Exception {
//        // 1、创建ScheduledExecutorService实例
//        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
//        TimerTask timerTask = new TimerTask(4000); // 任务需要 4000 ms 才能执行完毕
//        // 2、延时 1 秒后，按 1 h的周期执行任务
//        timer.scheduleAtFixedRate(timerTask, 1000, 3000 * 60 * 20, TimeUnit.MILLISECONDS);
//
//        // 3、启动服务
//        final Server server = new Server(ci, Executors.newSingleThreadScheduledExecutor());
//        try {
//            // 4、连接到服务
//            server.connect();
//            // add sync access, poll every 500 ms，启动一个同步的access用来读取地址上的值，线程池每500ms读值一次
//            // 5、这个是用来循环读值的，只读一次值不用这样
//            final AccessBase access = new SyncAccess(server, 500);
//
//            // 6、遍历每个OPC点表值，对每个值进行操作
//            for (String str : allItems) {
//                access.addItem(str, new DataCallback() {
//                    //                    @Override
//                    public void changed(Item item, ItemState itemState) {
////                        System.out.println("点表总数:" + allOPC.size());
//
//                        try {
//                            if (itemState.getValue().getType() == 8) {
//                                value = itemState.getValue().getObjectAsString().getString();
//                            } else {
//                                value = itemState.getValue().getObject();
//                            }
//                            String equipmenttime = sdf.format(itemState.getTimestamp().getTime()).replaceAll(" ", "-");
//                            String opcname = item.getId().contains("DA.") ? item.getId().split("\\.")[1] : item.getId();
//                            String opcvalue = value.toString() == "true" ? "1" : value.toString() == "false" ? "0" : value.toString();
//                            String status = (itemState.getQuality() == 192 ? "Good" : "Bad");
//                            String collectiontime = sdf.format(new Date()).replaceAll(" ", "-"); //2020-06-04-10:42
//                            String mapoOPCValue = equipmenttime + "," + opcvalue + "," + status + "," + collectiontime;
//                            if (!mapoOPCValue.equals(allOPC.get(opcname))) {
//                                File file = new File("F:\\liulizhong\\opcdata\\" + collectiontime.substring(0, 7) + "\\" + collectiontime.substring(0, 10) + ".txt");
//                                File fileParents = new File("F:\\liulizhong\\opcdata\\" + collectiontime.substring(0, 7));
//                                if (!fileParents.exists()) {
//                                    fileParents.mkdirs();
//                                }
//                                if (!file.exists()) {
//                                    file.createNewFile();
//                                    allOPC.clear();
//                                }
//                                BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
//                                bw.write(opcname + "," + mapoOPCValue + "\r\n");
//                                bw.flush();
//                                allOPC.put(opcname, mapoOPCValue);
//                            }
//                            System.out.println(++nums + ";" + collectiontime);
//
//                            // 读值后推送到Kafka的"test"主题
////                            if (item.getId().contains("DA.")) {
////                                if (value.toString() == "true") {
////                                    producer.send(new ProducerRecord<String, String>("test", sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId().split("\\.")[1] + "---" + "1" + "---" + (itemState.getQuality() == 192 ? "Good" : "Bad") + "---" + sdf.format(new Date())));
////                                } else if (value.toString() == "false") {
////                                    producer.send(new ProducerRecord<String, String>("test", sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId().split("\\.")[1] + "---" + "0" + "---" + (itemState.getQuality() == 192 ? "Good" : "Bad") + "---" + sdf.format(new Date())));
////                                } else {
////                                    producer.send(new ProducerRecord<String, String>("test", sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId().split("\\.")[1] + "---" + value + "---" + (itemState.getQuality() == 192 ? "Good" : "Bad") + "---" + sdf.format(new Date())));
////                                }
////                            } else {
////                                if (value.toString() == "true") {
////                                    producer.send(new ProducerRecord<String, String>("test", sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId() + "---" + "1" + "---" + (itemState.getQuality() == 192 ? "Good" : "Bad") + "---" + sdf.format(new Date())));
////                                } else if (value.toString() == "false") {
////                                    producer.send(new ProducerRecord<String, String>("test", sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId() + "---" + "0" + "---" + (itemState.getQuality() == 192 ? "Good" : "Bad") + "---" + sdf.format(new Date())));
////                                } else {
////                                    producer.send(new ProducerRecord<String, String>("test", sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId() + "---" + value + "---" + (itemState.getQuality() == 192 ? "Good" : "Bad") + "---" + sdf.format(new Date())));
////                                }
////                            }
//                            // bw1.write(sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId() + "---" + value  + "---" + itemState.getQuality()  + "---" + sdf.format(new Date()) + "\r\n");
//
////                            System.out.println("value:" + value);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                System.out.println("Map长度：" + allOPC.size());
//            }
//            // start reading，开始读值
//            access.bind();
//
//            while (true) {
//                Thread.sleep(1000);
//            }
//            // stop reading，停止读取
//            //access.unbind();
//        } catch (final JIException e) {
//            System.out.println(String.format("%08X: %s", e.getErrorCode(), server.getErrorMessage(e.getErrorCode())));
//        } finally {
//
//        }
//    }
//
//    /**
//     * 定时获取OPC中各所有点表名，放到allItems中
//     *
//     * @return
//     * @throws Exception
//     */
//    public static ArrayList dumpFlat() throws Exception {
//        Server server2 = new Server(ci, Executors.newSingleThreadScheduledExecutor());
//        server2.connect();
//
//        ArrayList arr = new ArrayList<String>();
//        for (String name : server2.getFlatBrowser().browse()) {
//            arr.add(name);
//        }
//
//        server2.disconnect();
//
//        return arr;
//    }
//
//    /**
//     * 多线程方式更新allOPC信息
//     */
//    private static class TimerTask implements Runnable {
//
//        private final int sleepTime;
//        private final SimpleDateFormat dateFormat;
//
//        public TimerTask(int sleepTime) {
//            this.sleepTime = sleepTime;
//            dateFormat = new SimpleDateFormat("HH:mm:ss");
//        }
//
//        //        @Override
//        public void run() {
//            try {
//                Thread.sleep(sleepTime);
//                //获取所有点表名
//                allItems = dumpFlat();
//            } catch (Exception ex) {
//                ex.printStackTrace(System.err);
//            }
//        }
//    }
//
//    /**
//     * main主入口函数，调用readData
//     *
//     * @param args
//     * @throws Exception
//     */
//    public static void main(String[] args) throws Exception {
//        readData();
//    }
//}
