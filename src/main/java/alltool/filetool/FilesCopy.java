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
public class FilesCopy {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        //copyFileIO("D:\\software\\idea\\ideaIU-2018.2.5.exe", "d://ideaIU-2018.2.5.exe");     //用时6950
        copyFileIOFast(
                "\\\\10.238.255.198\\liulizhong\\opcdata\\2020-06\\2020-06-08.txt",
                "F:\\liulizhong\\opcdata\\2020-06\\2020-06-08.txt");            //用时5380
        long end = System.currentTimeMillis();
        System.out.println("复制完成，时间为： " + (start - end));
    }

    /**
     * 普通文件复制
     * @param src
     * @param dec
     * @throws IOException
     */
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

    /**
     * 文件复制(带缓冲IO：BufferedInputStream / BufferedOutputStream)
     * @param src
     * @param dec
     * @throws IOException
     */
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
