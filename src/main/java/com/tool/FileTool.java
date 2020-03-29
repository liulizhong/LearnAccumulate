package com.tool;

import org.junit.Test;

import java.io.File;

/**
 * @author lizhong.liu
 * @version TODO
 * @CalssName RenameFileName
 * @create 2020-03-26 9:08
 * @Des TODO
 */
public class FileTool {
    @Test
    public void reNameFile() throws Exception {
        reNameFiles(new File("C:\\Users\\liu31\\Desktop\\查阅文档"),"网查找","网上下载");
    }

    /**
     * ### 文件夹下递归改文件名
     * @param file
     * @param regex 正则匹配内容
     * @param replacement 更改后字符串
     * @throws Exception
     */
    public static void reNameFiles(File file,String regex,String replacement) throws Exception {
        if (!file.exists()) {
            throw new Exception("文件夹不存在");
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File newFile : files) {
                reNameFiles(newFile,regex,replacement);
            }
        } else if (file.isFile()) {
            file.renameTo(new File(file.getCanonicalPath().replaceAll(regex, replacement)));
        }
    }
}
