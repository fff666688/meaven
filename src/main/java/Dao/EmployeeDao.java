package Dao;

import com.aaa.Employee;
import org.apache.ibatis.annotations.Param;

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

    // ================= #{} 与 ${} 对比 =================

    /** 用 #{} 按姓名查：预编译，防注入 */
    List<Employee> selectByNameHash(@Param("empName") String empName);

    /** 用 ${} 按姓名查：字符串拼接，不防注入（XML 里要自己加引号） */
    List<Employee> selectByNameDollar(@Param("empName") String empName);

    /** 用 #{} 做排序：会变成 order by ?，排序失效 */
    List<Employee> orderByHash(@Param("column") String column);

    /** 用 ${} 做排序：字段名拼进 SQL，排序生效（必须自己做白名单校验） */
    List<Employee> orderByDollar(@Param("column") String column);

    /** 模糊查询：#{} 写法（推荐） */
    List<Employee> searchByLikeHash(@Param("keyword") String keyword);

    /** 模糊查询：${} 写法（有注入风险） */
    List<Employee> searchByLikeDollar(@Param("keyword") String keyword);
    List<Employee> selectByName(String name);

    /**
     * 模糊查询（课程 12 版）
     * XML 里用 concat('%', #{name}, '%')，% 拼接交给数据库，不写进 #{} 里
     */
    List<Employee> selectByLikeName(@Param("name") String name);
    Employee selectById(int id);
}
