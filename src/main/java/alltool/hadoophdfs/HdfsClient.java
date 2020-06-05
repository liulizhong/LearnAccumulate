package alltool.hadoophdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author lizhong.liu
 * @version TODO
 * @class ??
 * @CalssName HdfsClient
 * @create 2020-06-05 10:18
 * @Des TODO
 */
public class HdfsClient {
    @Test
    public void testMkdirs() throws IOException, InterruptedException, URISyntaxException {

        // 1、获取文件系统
        Configuration configuration = new Configuration();
        // 可配置在集群上运行
        // configuration.set("fs.defaultFS", "hdfs://hadoop102:9000");
        // FileSystem fs = FileSystem.get(configuration);
        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.10.10:8020"), configuration, "hdfs");

        // 2 创建目录
        fs.mkdirs(new Path("/tmp/test/haha"));

        // 3、上传文件
        fs.copyFromLocalFile(new Path("e:/banzhang.txt"), new Path("/banzhang.txt"));

        // 4、文件下载
        // boolean delSrc 指是否将原文件删除
        // Path src 指要下载的文件路径
        // Path dst 指将文件下载到的路径
        // boolean useRawLocalFileSystem 是否开启文件校验
        fs.copyToLocalFile(false, new Path("/banzhang.txt"), new Path("e:/banhua.txt"), true);

        // 5、文件夹删除
        fs.delete(new Path("/0508/"), true);

        // 6、文件名更改
        fs.rename(new Path("/banzhang.txt"), new Path("/banhua.txt"));

        // 7、查看文件详情
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);
        while (listFiles.hasNext()) {
            LocatedFileStatus status = listFiles.next();
            // (1)文件名称
            System.out.println(status.getPath().getName());
            // (2)长度
            System.out.println(status.getLen());
            // (3)权限
            System.out.println(status.getPermission());
            // (4)分组
            System.out.println(status.getGroup());
            // (5)获取存储的块信息
            BlockLocation[] blockLocations = status.getBlockLocations();
            for (BlockLocation blockLocation : blockLocations) {
                // 获取块存储的主机节点
                String[] hosts = blockLocation.getHosts();
                for (String host : hosts) {
                    System.out.println(host);
                }
            }

            // 7、文件和文件夹的判断
            FileStatus[] listStatus = fs.listStatus(new Path("/"));
            for (FileStatus fileStatus : listStatus) {
                // 如果是文件
                if (fileStatus.isFile()) {
                    System.out.println("f:"+fileStatus.getPath().getName());
                }else {
                    System.out.println("d:"+fileStatus.getPath().getName());
                }
            }

            // 8、文件上传的IO流
                // (1) 创建输入流
            FileInputStream ipFis = new FileInputStream(new File("e:/banhua.txt"));
                // (2)获取输出流
            FSDataOutputStream upFos = fs.create(new Path("/banhua.txt"));
                // (3)流对拷
            IOUtils.copyBytes(ipFis, upFos, configuration);
                // (4)关闭资源
            IOUtils.closeStream(upFos);
            IOUtils.closeStream(ipFis);

            // 9、文件下载的IO流
                // (1)获取输入流
            FSDataInputStream downFis = fs.open(new Path("/banhua.txt"));
                // (2)获取输出流
            FileOutputStream downFos = new FileOutputStream(new File("e:/banhua.txt"));
                // (3)流的对拷
            IOUtils.copyBytes(downFis, downFos, configuration);
                // (4)关闭资源
            IOUtils.closeStream(downFos);
            IOUtils.closeStream(downFis);

            // 10、关闭资源
            fs.close();
        }
    }
}