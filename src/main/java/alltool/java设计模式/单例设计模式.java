package alltool.java设计模式;

/**
 * @author lizhong.liu
 * @version TODO
 * @class ??
 * @CalssName 单例设计模式
 * @create 2021-03-03 15:24
 * @Des TODO
 */

// 【饿汉式】 - 枚举
public enum 单例设计模式 {
    INSTANCE
}

// 【饿汉式】 - 直接创建
class Hungry {
    public static final Hungry INSTANCE = new Hungry();

    private Hungry() {
    }
}

// 【饿汉式】 - 方法返回
class Hungry2 {
    private static final Hungry2 INSTANCE = new Hungry2();

    private Hungry2() {
    }

    public static Hungry2 getInstance() {
        return INSTANCE;
    }
}

// 【懒汉式】 -
class Lazy {
    private static Lazy instance;

    private Lazy() {
    }

    public static Lazy getInstance() {
        if (instance == null) {//为了提高效率用
            synchronized (Lazy.class) {
                if (instance == null) {//为了保证唯一的对象用的
                    instance = new Lazy();
                }
            }
        }
        return instance;
    }
}
