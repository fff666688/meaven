package org.example;

import com.aaa.Employee;
import Dao.EmployeeDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AppTest10 {

    @Test
    public void test1() throws Exception {   // 批量删除
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sessionFactory.openSession();

        EmployeeDao mapper = session.getMapper(EmployeeDao.class);

        List<Integer> ids = Arrays.asList(3, 5);
        int i = mapper.batchDelete(ids);
        session.commit();
        System.out.println("批量删除 受影响行数: " + i);
        session.close();
    }

    @Test
    public void test2() throws Exception {   // 批量添加
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sessionFactory.openSession();

        EmployeeDao mapper = session.getMapper(EmployeeDao.class);

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(null, "陈朋鸟", "男", 17, "厕所部", "程序员", 50.0, new Date(), null, null));
        employees.add(new Employee(null, "陈月月", "女", 26, "市场部", "运营", 8888.0, new Date(), "13800000002", "test2@qq.com"));

        int i = mapper.batchAdd(employees);
        session.commit();
        System.out.println("批量添加 受影响行数: " + i);
        session.close();
    }
}
