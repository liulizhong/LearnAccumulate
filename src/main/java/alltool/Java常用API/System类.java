package alltool.Java常用API;

import java.io.PrintStream;

public class System类 {   //// java.lang.System
    public static void main(String[] args) throws Exception {
		System.out.println("out：hello");                            // 标准的输出设备，控制台
		System.err.println("err：hello");                            // 打印错误信息，默认用红色
        System.in.read();                                              // ：标准的输入设备，键盘
		long start = System.currentTimeMillis();                 //1540281038928   当前系统时间距离1970-1-1 0:0:0 0毫秒的毫秒差
//		System.arraycopy(src, srcPos, dest, destPos, length);     // copy数组
        System.gc();                                              // 通知垃圾回收器来回收垃圾
        System.getProperties();                                   //  获取当前系统的属性
        System.setOut(new PrintStream("1.txt"));      // 输出到文件相对目录下“1.txt”
        System.exit(0);                                   // 终止当前正在运行的 Java 虚拟机。非0是异常终止，0是正常退出
    }
}
