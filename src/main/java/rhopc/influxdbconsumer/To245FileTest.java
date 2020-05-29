package rhopc.influxdbconsumer;


import com.jcraft.jsch.*;

import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lizhong.liu
 * @version TODO
 * @class 测试环境
 * @CalssName To245FileTest
 * @create 2020-05-26 9:49
 * @Des TODO
 */
public class To245FileTest {
    /**
     * 消费所有数据到245服务器的Influxdb
     */
    static String basePath = "/home/liulizhong/module/influxdb/kafkaopc/";
    static JSch jsch = null;
    static Session session = null;
    static Channel channel = null;
    static ChannelSftp sftp = null;

    static {
        System.out.println("一：session:" + session + ", channel:" + channel + ", sftp:" + sftp);

        //【二】、服务器连接
        jsch = new JSch();
        try {
            session = jsch.getSession("root", "192.168.1.245", 22);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        // 设置登陆主机的密码
        session.setPassword("abc@123@!@#");// 设置密码
        // 设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        // 设置登陆超时时间
        try {
            session.connect(300000);
            channel = (Channel) session.openChannel("sftp");
            channel.connect(10000000);
            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            e.printStackTrace();
        }
        System.out.println("二：session:" + session + ", channel:" + channel + ", sftp:" + sftp);
    }

    public static void main(String[] args) throws Exception {
        SumConsumer();
    }

    /**
     * 全部数据
     */
    public static void SumConsumer() throws Exception {
        String filePash = "";
        String parentsfilePash = "";
        File file;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int num = 0;
        while (true) {
            for (int record = 0; record < 1000; record++) {
                //1、每次循环创建新的文件夹判断
                filePash = basePath + dateFormat.format(new Date()).substring(0, 7) + "/" + dateFormat.format(new Date()).substring(0, 10) + ".txt";
                parentsfilePash = basePath + dateFormat.format(new Date()).substring(0, 7);
                sftp.cd(basePath + dateFormat.format(new Date()).substring(0, 7));
                //判断父文件夹是否存在，不存在即创建
                SftpATTRS attrs = null;
                try {
                    attrs = sftp.stat(parentsfilePash);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                if (attrs == null) {
                    sftp.mkdir(parentsfilePash);
                }
                //文件输出
                OutputStream o = null;
                file = new File(filePash);
                o = sftp.put(file.getName(), ChannelSftp.APPEND);
                String recordString = "opcall,opcname=" + record + " status=" + record + ",equipmenttime=" + record + ",opcvalue=" + record + " " + System.currentTimeMillis() + "\r\n";
                o.write(recordString.getBytes("UTF-8"));
                System.out.println(recordString + " ===数据条数：" + ++num);
                o.close();
            }
        }
    }
}
