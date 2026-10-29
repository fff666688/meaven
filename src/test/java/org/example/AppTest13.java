package org.example;

import Dao.EmployeeDao;
import Util.SqlSessionFactoryUtil;
import com.aaa.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.Reader;

/**
 * 一级缓存：SqlSession 级，默认开启
 * 二级缓存：namespace 级，需要 EmployeeDao.xml 里加 <cache/>，跨 SqlSession 生效
 * 判断方法：看控制台有没有打印 ==>  Preparing。没有 = 命中缓存，没走数据库。
 */
public class AppTest13 {

    @Test
    public void testOneLevelCache() {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();

        // ============ 同一个 SqlSession 内：第2、3次命中一级缓存 ============
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeDao dao1 = sqlSession.getMapper(EmployeeDao.class);

            System.out.println("=========== 第1次查询：会执行 SQL ===========");
            Employee emp1 = dao1.selectById(1);

            System.out.println("=========== 第2次查询：不执行 SQL，命中一级缓存 ===========");
            Employee emp2 = sqlSession.getMapper(EmployeeDao.class).selectById(1);
            System.out.println("两次是同一个对象吗？" + (emp1 == emp2));

            System.out.println("=========== 第3次查询：仍然命中缓存 ===========");
            System.out.println(sqlSession.getMapper(EmployeeDao.class).selectById(1));
        }

        // ============ 换 SqlSession：一级缓存没了，但有二级缓存 ============
        try (SqlSession sqlSession2 = sqlSessionFactory.openSession()) {
            EmployeeDao dao4 = sqlSession2.getMapper(EmployeeDao.class);

            System.out.println("=========== 第4次查询：新 SqlSession，一级缓存没了，会执行 SQL ===========");
            System.out.println(dao4.selectById(1));

            System.out.println("=========== 第5次查询：不执行 SQL，命中一级缓存 ===========");
            System.out.println(dao4.selectById(1));

            System.out.println("=========== 第6次：update+commit 会清空一级和二级缓存，会执行 SQL ===========");
            Employee updateOne = new Employee();
            updateOne.setEmpId(1);
            updateOne.setDept("研发部");      // 故意写回原值，只为触发清缓存，不污染数据
            System.out.println("update 影响行数：" + dao4.update(updateOne));
            sqlSession2.commit();

            System.out.println(dao4.selectById(1));

            System.out.println("=========== 第7次查询：不执行 SQL，缓存又有了 ===========");
            System.out.println(dao4.selectById(1));

            System.out.println("=========== clearCache 后第8次查询：会执行 SQL ===========");
            sqlSession2.clearCache();
            System.out.println(dao4.selectById(1));

            System.out.println("=========== 第9次查询：不执行 SQL ===========");
            System.out.println(dao4.selectById(1));
        }
    }
    //测试二级缓存
    @Test
    public void test2() throws Exception {
        //1.读取配置文件---解析配置文件. mybatis知道连接的数据库和账号密码。 知道映射文件中内。
        Reader reader = Resources.getResourceAsReader("conf.xml");
        //2.获取SqlSessionFactory对象
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        //1. 获取SqlSession对象----理解为: Connection对象。类中包含对表的各种操作方法。
        SqlSession session = sessionFactory.openSession();
        System.out.println("第一次查询数据库 id=2的数据内容----执行sql语句。");
        EmployeeDao mapper1 = session.getMapper(EmployeeDao.class);
        Employee employee1 = mapper1.selectById(2);
        System.out.println(employee1);

        System.out.println("第二次查询数据库，id=2的数据内容----没有执行sql语句。");
        EmployeeDao mapper2 = session.getMapper(EmployeeDao.class);
        Employee employee2 = mapper2.selectById(2);
        System.out.println(employee2);

        session.close();//关闭session 一级缓存就失效了
        SqlSession session2 = sessionFactory.openSession();
        System.out.println("第三次查询数据库，id=2的数据内容----没有执行sql---从二级缓存获取。");
        EmployeeDao mapper3 = session2.getMapper(EmployeeDao.class);
        Employee employee3 = mapper3.selectById(2);
        System.out.println(employee3);


        System.out.println("第四次查询数据库，id=2的数据内容----没有执行sql---从二级缓存。");
        EmployeeDao mapper4 = session2.getMapper(EmployeeDao.class);
        Employee employee4 = mapper4.selectById(2);
        System.out.println(employee4);

        System.out.println("第五次查询数据库，id=2的数据内容----没有执行sql---从二级缓存。");
        EmployeeDao mapper5 = session2.getMapper(EmployeeDao.class);
        Employee employee5 = mapper5.selectById(2);
        System.out.println(employee5);

    }
}
