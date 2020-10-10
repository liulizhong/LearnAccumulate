package alltool.filetool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author lizhong.liu
 * @version TODO
 * @class ??
 * @CalssName FilesToFile
 * @create 2020-09-25 15:09
 * @Des TODO
 */
public class FilesCountLines {
    public static void main(String[] args) throws IOException {
        // 1、简单计算一个文件的总行数
        System.out.println(Files.lines(Paths.get("C:\\tmp\\oneopcall.txt")).count());
/*      // 2、计算一个文件夹下所有文件的总行数
        String[] src = {"C:\\tmp\\result"};
        Long count = 0L;
        for (String arg : src) {
            File file = new File(arg);
            File[] files = file.listFiles();
            for (File file1 : files) {
                count += countFileLines(file1.getAbsolutePath());
            }
        }
        System.out.println("count:" + count);
*/
    }

    public static Long countFileLines(String dir) throws IOException {
        long count = Files.lines(Paths.get(dir)).count();

        return count;
    }
}
