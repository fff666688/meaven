package org.example;

import com.aaa.Employee;
import Dao.EmployeeDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.Reader;
import java.util.List;

/**
 * #{} 和 ${} 的区别验证
 * 重点看控制台打印的 Preparing / Parameters 两行
 */
public class AppTest09 {

    /** 复用 session，避免每个方法重复写 */
    private SqlSession openSession() throws Exception {
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
        return factory.openSession();
    }

    /**
     * 测试1：SQL 注入
     * 传入 aaa' or '1'='1
     * #{empName} -> where emp_name = ?        参数是整串      -> 查不到，0 条
     * ${empName} -> where emp_name = 'aaa' or '1'='1'          -> 条件恒真，全表返回
     */
    @Test
    public void testInjection() throws Exception {
        String attack = "aaa' or '1'='1";

        try (SqlSession session = openSession()) {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);

            List<Employee> list1 = mapper.selectByNameHash(attack);
            System.out.println(">>> #{} 查到条数 = " + list1.size());

            List<Employee> list2 = mapper.selectByNameDollar(attack);
            System.out.println(">>> ${} 查到条数 = " + list2.size());
        }
    }

    /**
     * 测试2：排序字段
     * order by #{column} -> order by ?   排的是字符串常量，顺序不变
     * order by ${column} -> order by salary  真正按薪资排序
     */
    @Test
    public void testOrderBy() throws Exception {
        try (SqlSession session = openSession()) {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);

            List<Employee> list1 = mapper.orderByHash("salary");
            System.out.println(">>> order by #{} 结果：");
            list1.forEach(e -> System.out.println(e.getEmpName() + "  " + e.getSalary()));

            List<Employee> list2 = mapper.orderByDollar("salary");
            System.out.println(">>> order by ${} 结果：");
            list2.forEach(e -> System.out.println(e.getEmpName() + "  " + e.getSalary()));
        }
    }

    /**
     * 测试3：模糊查询
     * #{} 要配合 concat，% 放在外面
     * ${} 直接 '%值%'，能跑但会被 LIKE 通配符和引号攻击
     */
    @Test
    public void testLike() throws Exception {
        String keyword = "王";

        try (SqlSession session = openSession()) {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);

            System.out.println(">>> like #{} =" + mapper.searchByLikeHash(keyword).size());
            System.out.println(">>> like ${} =" + mapper.searchByLikeDollar(keyword).size());
        }
    }
}
