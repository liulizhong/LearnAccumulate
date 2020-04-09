package alltool.mybatis;

/**
 * @author lizhong.liu
 * @version TODO
 * @class ??
 * @CalssName Department
 * @create 2020-04-06 14:58
 * @Des TODO
 */
public class Department {
    private int id;
    private String name;

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Department() {
    }
}
