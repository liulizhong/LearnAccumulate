package rh.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * 2020-04-01(ago).txt
 * count:336174685
 * bad:71376210
 * bad数据占比：21.23%
 *
 * 2020-04-01.txt
 * count:772795683
 * bad:230189719
 * bad数据占比：29.79%
 */

public class DataQualityAnalysis {
    public static void main(String[] args) {
        double badRate = fileCounts(new File("C:\\Users\\Administrator\\Desktop\\clph.txt"));
        System.out.println("bad数据占比：" + String.format("%.2f", badRate*100) + "%");
    }

    public static double fileCounts(File file){
        int count = 0;
        int bad = 0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                if(s.contains("------") || s.split("---").length != 4){
                    bad++;
                }else {
                    if(Integer.parseInt(s.split("---")[3]) < 192 || Integer.parseInt(s.split("---")[3]) > 219) {
                        bad++;
                    }
                }
                count++;
            }

            System.out.println("count:" + count);
            System.out.println("bad:" + bad);

            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return (double) bad / (double) count;
    }
}
