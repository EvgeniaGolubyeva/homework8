package auction.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.io.IOException;

/**
 * @author Evgenia
 */


//according to http://mybatis.github.io/mybatis-3/getting-started.html
//SqlSessionFactory should exist for the duration of application execution
@Singleton
public class SqlSessionFactoryHolder {
    private SqlSessionFactory sessionFactory;

    @PostConstruct
    public void init() throws IOException {
        this.sessionFactory =
                new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config.xml"));
    }

    public SqlSessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
