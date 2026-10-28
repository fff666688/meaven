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

public class AppTest07 {

    @Test
    public void test1() throws Exception {
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sessionFactory.openSession();

        EmployeeDao mapper = session.getMapper(EmployeeDao.class);
        Employee condition = new Employee();
        condition.setEmpName("王浩");
        condition.setDept("研发部");

        List<Employee> list = mapper.selectByCondition2(condition);
        System.out.println("情况1 查到条数: " + list.size());
        list.forEach(System.out::println);
        session.close();
    }

    @Test
    public void test2() throws Exception {
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sessionFactory.openSession();

        EmployeeDao mapper = session.getMapper(EmployeeDao.class);
        Employee condition = new Employee();
        condition.setDept("研发部");

        List<Employee> list = mapper.selectByCondition2(condition);
        System.out.println("情况2 查到条数: " + list.size());
        session.close();
    }

    @Test
    public void test3() throws Exception {
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sessionFactory.openSession();

        EmployeeDao mapper = session.getMapper(EmployeeDao.class);
        Employee condition = new Employee();

        List<Employee> list = mapper.selectByCondition2(condition);
        System.out.println("情况3 查到条数: " + list.size());
        session.close();
    }
}
