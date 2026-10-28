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

public class AppTest06 {

    //1.读取配置文件---解析配置文件。mybatis知道连接的数据库和账号密码。 知道映射文件中内容。
    //2.获取SqlSessionFactory对象
    //3.获取SqlSession对象----理解为：Connection对象。其中包含对表的各种操作方法。
    //4.获取EmployeeDao的代理实现类对象。

    @Test
    public void test1() throws Exception {   // 情况1：name+dept 都传，choose 只走第一个 when
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
    public void test2() throws Exception {   // 情况2：只传 dept，走第二个 when
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
    public void test3() throws Exception {   // 情况3：什么都不传，走 otherwise 的 1=1
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
