package org.example;

import com.aaa.Employee;
import Dao.EmployeeDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.Reader;

public class AppTest09 {

    @Test
    public void test1() throws Exception {   // 情况1：只改一个字段
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sessionFactory.openSession();

        EmployeeDao mapper = session.getMapper(EmployeeDao.class);
        Employee emp = new Employee();
        emp.setEmpId(1);
        emp.setJob("人事部");

        int i = mapper.update(emp);
        session.commit();
        System.out.println("情况1 受影响行数: " + i);
        session.close();
    }

    @Test
    public void test2() throws Exception {   // 情况2：同时改多个字段
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sessionFactory.openSession();

        EmployeeDao mapper = session.getMapper(EmployeeDao.class);
        Employee emp = new Employee();
        emp.setEmpId(2);
        emp.setEmpName("李晓萌");
        emp.setDept("产品部");
        emp.setSalary(12000.0);

        int i = mapper.update(emp);
        session.commit();
        System.out.println("情况2 受影响行数: " + i);
        session.close();
    }

    @Test
    public void test3() throws Exception {   // 情况3：改一个不存在的empId
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sessionFactory.openSession();

        EmployeeDao mapper = session.getMapper(EmployeeDao.class);
        Employee emp = new Employee();
        emp.setEmpId(9999);
        emp.setJob("不存在的人");

        int i = mapper.update(emp);
        session.commit();
        System.out.println("情况3 受影响行数: " + i);
        session.close();
    }
}
