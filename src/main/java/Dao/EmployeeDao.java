package Dao;

import com.aaa.Employee;

import java.util.List;

public interface EmployeeDao {
    public List<Employee> selectByCondition(Employee employee);
    public List<Employee> selectByCondition2(Employee employee);
    public List<Employee> selectByCondition3(Employee employee);
    public List<Employee> selectByCondition4(Employee employee);
    public int update(Employee employee);
    public int batchDelete(List<Integer> ids);

    /**
     * 批量添加
     */
    public int batchAdd(List<Employee> emps);
}
