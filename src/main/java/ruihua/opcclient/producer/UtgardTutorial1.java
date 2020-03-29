package ruihua.opcclient.producer;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;

import com.rh.utils.GetItems;
import com.rh.utils.GetNames;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
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
public class UtgardTutorial1 {
    static Object value = "";

    public static void main(String[] args) throws Exception {
        //产量平衡所有点表名    <点表名,clph>
        Map<String, String> clph = GetNames.clph();

        Properties props = new Properties();
        props.put("bootstrap.servers", "10.238.255.151:9092,10.238.255.152:9092,10.238.255.153:9092");
        props.put("group.id", "test");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        FileWriter fw = new FileWriter(new File("C:\\Users\\Administrator\\Desktop\\clph.txt"), true);
//        BufferedWriter bw = new BufferedWriter(fw);
//
//        FileWriter fw1 = new FileWriter(new File("C:\\Users\\Administrator\\Desktop\\sum.txt"), true);
//        BufferedWriter bw1 = new BufferedWriter(fw1);

        // 连接信息
        final ConnectionInformation ci = new ConnectionInformation();
        ci.setHost("localhost");         // 电脑IP
        ci.setDomain("");                  // 域，为空就行
        ci.setUser("administrator");             // 电脑上自己建好的用户名
        ci.setPassword("ts.1234");          // 用户名的密码

        ci.setClsid("04524449-C6B2-4d62-8471-C64FA1DDF64F");
        // KEPServer的注册表ID，可以在“组件服务”里看到
        //final String itemId = "DA.$Time";    // 项的名字按实际，没有实际PLC，用的模拟器：simulator

        // 启动服务
        final Server server = new Server(ci, Executors.newSingleThreadScheduledExecutor());

        List<String> qqq = GetItems.dumpFlat();

        try {
            // 连接到服务
            server.connect();
            // add sync access, poll every 500 ms，启动一个同步的access用来读取地址上的值，线程池每500ms读值一次
            // 这个是用来循环读值的，只读一次值不用这样
            final AccessBase access = new SyncAccess(server, 500);
            // 这是个回调函数，就是读到值后执行这个打印，是用匿名类写的，当然也可以写到外面去
            for (String str : qqq) {
            access.addItem(str, new DataCallback() {
                @Override
                public void changed(Item item, ItemState itemState) {

                    try {
                        if (itemState.getValue().getType() == 8) {
                            value = itemState.getValue().getObjectAsString().getString();
                        } else {
                            value = itemState.getValue().getObject();
                        }


                        if(clph.get(item.getId().substring(3)) == "clph"){
                            producer.send(new ProducerRecord<String, String>("clph",sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId() + "---" + value  + "---" + itemState.getQuality()));
//                            bw.write(sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId() + "---" + value  + "---" + itemState.getQuality() + "\r\n");
                        }
                        producer.send(new ProducerRecord<String, String>("test",sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId() + "---" + value  + "---" + itemState.getQuality()));
//                        bw1.write(sdf.format(itemState.getTimestamp().getTime()) + "---" + item.getId() + "---" + value  + "---" + itemState.getQuality() + "\r\n");

                        System.out.println("value:" + value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });
        }
            // start reading，开始读值
            access.bind();

            while (true){
                Thread.sleep( 1000);
            }
            // stop reading，停止读取
            //access.unbind();
        } catch (final JIException e) {
            System.out.println(String.format("%08X: %s", e.getErrorCode(), server.getErrorMessage(e.getErrorCode())));
        } finally {
            producer.close();
        }
    }
}
