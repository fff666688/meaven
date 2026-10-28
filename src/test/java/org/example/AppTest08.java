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

public class AppTest08 {

    @Test
    public void test1() throws Exception {   // 情况1：name+dept 都传 —— and/or 都拼，or 连接
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sessionFactory.openSession();

        EmployeeDao mapper = session.getMapper(EmployeeDao.class);
        Employee condition = new Employee();
        condition.setEmpName("王浩");
        condition.setDept("研发部");

        List<Employee> list = mapper.selectByCondition3(condition);
        System.out.println("情况1 查到条数: " + list.size());
        list.forEach(System.out::println);
        session.close();
    }

    @Test
    public void test2() throws Exception {   // 情况2：只传 empName —— 只有第一段
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sessionFactory.openSession();

        EmployeeDao mapper = session.getMapper(EmployeeDao.class);
        Employee condition = new Employee();
        condition.setEmpName("王浩");

        List<Employee> list = mapper.selectByCondition3(condition);
        System.out.println("情况2 查到条数: " + list.size());
        list.forEach(System.out::println);
        session.close();
    }

    @Test
    public void test3() throws Exception {   // 情况3：什么都不传 —— trim 内容为空，prefix 不加，无 where
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sessionFactory.openSession();

        EmployeeDao mapper = session.getMapper(EmployeeDao.class);
        Employee condition = new Employee();

        List<Employee> list = mapper.selectByCondition3(condition);
        System.out.println("情况3 查到条数: " + list.size());
        session.close();
    }
}
