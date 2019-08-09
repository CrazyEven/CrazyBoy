package main.crazyJava.crazyJdbc.hibernate.session;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
    private static SessionFactory sessionFactory;

    /**
     * getCurrentSession在事物提交或者回滚之后会自动关闭
     * @return
     */
    public static Session getSession() {
        return getSessionFactory().getCurrentSession();
    }

    /**
     *
     * @return
     */
    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
