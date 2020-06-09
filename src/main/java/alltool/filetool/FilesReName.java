package alltool.filetool;

import org.junit.Test;

import java.io.File;

/**
 * @author lizhong.liu
 * @version TODO
 * @class 递归修改文件夹下文件的名字(正则匹配)
 * @CalssName RenameFileName
 * @create 2020-03-26 9:08
 * @Des TODO
 */
public class FilesReName {
    @Test
    public void reNameFile() throws Exception {
        reNameFiles(new File(
                        "D:\\studyware\\bizdata_module\\9、韩顺平_Scala\\正式课程每天视频资料"),
                "scala核心编程-",
                "");
    }

    /**
     * 方法一： 文件夹下递归改文件名
     *
     * @param file
     * @param regex       正则匹配内容
     * @param replacement 更改后字符串
     * @throws Exception
     */
    public static void reNameFiles(File file, String regex, String replacement) throws Exception {
        if (!file.exists()) {
            throw new Exception("文件夹不存在");
        }
        if (file.isDirectory()) {
            //改目录名
            file.renameTo(new File(file.getCanonicalPath().replaceAll(regex, replacement)));
            File[] files = file.listFiles();
            if (!file.exists() || null == file || files.length == 0) {
            } else {
                for (File newFile : files) {
                    reNameFiles(newFile, regex, replacement);
                }
            }
        } else if (file.isFile()) {
            //该文件名
            file.renameTo(new File(file.getCanonicalPath().replaceAll(regex, replacement)));
        }
    }
}