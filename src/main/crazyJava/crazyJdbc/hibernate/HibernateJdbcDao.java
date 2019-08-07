package main.crazyJava.crazyJdbc.hibernate;

import main.crazyJava.carzyPojo.common.TechnologyEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateJdbcDao {

    public TechnologyEntity getEntity(String id){
        //加载默认配置文件
        //Configuration config = new Configuration().configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        //创建会话工厂对象
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        // 创建Transaction实例

        Session session = sessionFactory.openSession();

        //Transaction tx = session.beginTransaction();

        TechnologyEntity entity = session.get(TechnologyEntity.class,id);
        session.close();
        return entity;
    }

}
