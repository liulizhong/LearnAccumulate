package alltool.项目一电信客服hbase_mysql.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache extends LinkedHashMap<String, Integer> {
    private static final long serialVersionUID = 1L;
    protected int maxElements;

    public LRUCache(int maxSize) {
        super(maxSize, 0.75F, true);
        this.maxElements = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
        return (size() > this.maxElements);
    }
}
