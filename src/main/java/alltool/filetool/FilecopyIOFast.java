package alltool.filetool;

import java.io.*;

/**
 * @author lizhong.liu
 * @version TODO
 * @class windows间文件复制
 * @CalssName ShareFileToOtherComputerFile
 * @create 2020-06-09 14:11
 * @Des TODO
 */
public class FilecopyIOFast {
    public static void main(String[] args) throws Exception {
//        if (args.length != 1 || args[0].split("-").length != 3 || !args[0].split("-")[0].startsWith("202")) {
//            throw new Exception("请输入要同步的数据日期，或日期格式不对！！！！！！！！！！！");
//        }
//        String date = args[0];
        String date = "2020-06-10";
        String src = "D:\\tmp\\hive\\data.txt";
        String dec_event = "D:\\tmp\\hive\\event_log.txt";
        String dec_startup = "D:\\tmp\\hive\\startup_log.txt";
//        File fileParents = new File("F:\\liulizhong\\opcdata\\" + date.substring(0, 7));
//        if (!fileParents.exists()) {
//            fileParents.mkdirs();
//        }
//        long start = System.currentTimeMillis();
//        copyFileIO("D:\\software\\idea\\ideaIU-2018.2.5.exe","d://ideaIU-2018.2.5.exe"); //用时6950
        copyFileIOStr(
                src,
                dec_event,
                dec_startup);  //用时5380
//        long end = System.currentTimeMillis();
//        System.out.println("复制完成，时间为： " + (start - end));
    }

    private static void copyFileIOStr(String src, String dec_event, String dec_startup) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(src));
        BufferedWriter eventWriter = new BufferedWriter(new FileWriter(dec_event));
        BufferedWriter startupWriter = new BufferedWriter(new FileWriter(dec_startup));
        int num = 0;
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return;
            }
            if (readLine.contains("\"type\":\"event\"") && readLine.contains("\"type\":\"startup\"")) {
                System.out.println("数据有问题" + ++num);
            }else if (readLine.contains("\"type\":\"event\"")) {
                eventWriter.write(readLine + "\r\n");
            } else if (readLine.contains("\"type\":\"startup\"")) {
                startupWriter.write(readLine + "\r\n");
            } else {
                System.out.println("数据有问题" + ++num);
            }
        }
    }

    //普通复制文件
    private static void copyFileIO(String src, String dec) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(src);
        FileOutputStream fileOutputStream = new FileOutputStream(dec);
        int len;
        byte[] bytes = new byte[1024];
        while (true) {
            len = fileInputStream.read(bytes);
            if (len == -1) {
                break;
            }
            fileOutputStream.write(bytes);
        }
    }

    //高效复制文件
    private static void copyFileIOFast(String src, String dec) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(src));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(dec));
        int len;
        byte[] bytes = new byte[2048];
        while (true) {
            len = bufferedInputStream.read(bytes);
            if (len == -1) {
                break;
            }
            bufferedOutputStream.write(bytes);
        }
    }
}
