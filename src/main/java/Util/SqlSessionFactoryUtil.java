package Util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionFactoryUtil {

    private static final SqlSessionFactory FACTORY;

    static {
        try (InputStream in = Resources.getResourceAsStream("conf.xml")) {
            FACTORY = new SqlSessionFactoryBuilder().build(in);
        } catch (IOException e) {
            throw new ExceptionInInitializerError("加载 conf.xml 失败：" + e.getMessage());
        }
    }

    private SqlSessionFactoryUtil() {
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return FACTORY;
    }
}
