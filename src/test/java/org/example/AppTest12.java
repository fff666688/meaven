package org.example;

import Dao.EmployeeDao;
import com.aaa.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.Reader;
import java.util.List;

/**
 * 12、模糊查询：like concat('%', #{name}, '%')
 */
public class AppTest12 {

    @Test
    public void test1() throws Exception {
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sessionFactory.openSession();

        EmployeeDao mapper = session.getMapper(EmployeeDao.class);

        List<Employee> list = mapper.selectByLikeName("静");
        System.out.println(">>> 关键字[静] 查到条数: " + list.size());
        list.forEach(System.out::println);

        session.close();
    }

    /**
     * 对照：'%#{name}%' 这种错误写法查不到东西（#{} 会被当成 '?' 字面量）
     * 这里用课程写法 + 一个库里不存在的关键字，验证返回 0 条
     */
    @Test
    public void test2() throws Exception {
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sessionFactory.openSession();

        EmployeeDao mapper = session.getMapper(EmployeeDao.class);

        List<Employee> list = mapper.selectByLikeName("张");
        System.out.println(">>> 关键字[张] 查到条数: " + list.size() + "（库里没有姓张的，0 条是对的）");

        session.close();
    }
}
