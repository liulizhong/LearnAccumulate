package alltool.mysqljdbc;

/**
 * @author lizhong.liu
 * @version TODO
 * @CalssName Department
 * @create 2020-03-25 15:32
 * @Des TODO
 */
public class Employee {
    private int id;
    private String name;
    private Double grade;

    public Employee(int id, String name, Double grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public Employee() {
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade=" + grade +
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

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }
}
