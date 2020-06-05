package alltool.hadoophdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author lizhong.liu
 * @version TODO
 * @class ??
 * @CalssName file189to241hdfs
 * @create 2020-06-05 14:24
 * @Des TODO
 */
public class file189to241hdfs {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        // 1、获取文件系统
        Configuration configuration = new Configuration();
        configuration.set("fs.hdfs.impl",org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        // 可配置在集群上运行
        // configuration.set("fs.defaultFS", "hdfs://hadoop102:9000");
        // FileSystem fs = FileSystem.get(configuration);
        FileSystem fs = FileSystem.get(new URI("hdfs://10.238.255.175:18020"), configuration, "hdfs");

        // 2 创建目录
//        fs.mkdirs(new Path("/opc/test"));

        // 3、上传文件
        fs.copyFromLocalFile(new Path("F:\\liulizhong\\opcdata\\2020-06\\2020-06-04.txt"), new Path("/opc/test/2020-06-04.txt"));
    }
}