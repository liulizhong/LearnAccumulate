package alltool.项目一电信客服hbase_mysql.hbaseanalyze.mr;


import alltool.项目一电信客服hbase_mysql.hbaseanalyze.kv.CommDimension;
import alltool.项目一电信客服hbase_mysql.hbaseanalyze.kv.CountDurationValue;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class CountDurationReducer extends Reducer<CommDimension, Text, CommDimension, CountDurationValue> {

    private CountDurationValue countDuration = new CountDurationValue();

    @Override
    protected void reduce(CommDimension key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        //通话次数
        int callCount = 0;
        //通话时长
        int callDuration = 0;

        //遍历values，数据累加
        for (Text value : values) {
            callCount++;
            callDuration += Integer.parseInt(value.toString());
        }

        //封装value
        countDuration.setCallCount(callCount);
        countDuration.setCallDuration(callDuration);

        //写出
        context.write(key, countDuration);


    }
}
