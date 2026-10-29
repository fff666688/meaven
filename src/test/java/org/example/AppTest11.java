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

public class AppTest11 {
    @Test
    public void test1() throws Exception {   // 情况2：只传 empName —— 只有第一段
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sessionFactory.openSession();

        EmployeeDao mapper = session.getMapper(EmployeeDao.class);
       List<Employee> list = mapper.selectByName("AAA' or '1'='1");
    }
    @Test
    public void test2() throws Exception {
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sessionFactory.openSession();
        EmployeeDao mapper = session.getMapper(EmployeeDao.class);
        List<Employee> list = mapper.selectByLikeName("静");
        System.out.println(">>> 关键字[静] 查到条数: " + list.size());
        list.forEach(System.out::println);
        session.close();
    }
}

