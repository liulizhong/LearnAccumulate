package rhopc.utils;

/**
 *　点表详细信息
 */
public class Result {
    private String itemId;// 监控位置
    private Object value;// 监控值
    private String timeStamp;//监控时间

    public Result(String itemId, Object value, String timeStamp) {
        this.itemId = itemId;
        this.value = value;
        this.timeStamp = timeStamp;
    }

    public String getItemId() {
        return itemId;
    }

    public Object getValue() {
        return value;
    }

    public String getTimeStamp(){
        return timeStamp;
    }

    @Override
    public String toString() {
        return "[itemId=" + itemId + ", value=" + value + ",timeStamp=" + timeStamp + "]";
    }
}
