package alltool.mybatis;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author lizhong.liu
 * @version TODO
 * @class 所有方法接口类
 * @CalssName EmployeeMapper
 * @create 2020-04-06 10:13
 * @Des TODO
 */
public interface EmployeeMapper {

    //高级映射 => 自定义结果集
    Employee getEmployeAndDepById(Integer id);

    Employee getEmployeeById(Integer id);

    void addEmployee(Employee employee);

    void updateEmployee(Employee employee);

    void deleteEmployeeById(Integer id);

    List<Employee> getEmployees();

    Employee getEmployeeByLastNameAndEmail(@Param("lastName") String lastName, @Param("email") String email);

    Employee getEmployeeByMap(Map<String, Object> map);
}
