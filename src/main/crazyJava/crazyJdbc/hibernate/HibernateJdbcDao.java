package main.crazyJava.crazyJdbc.hibernate;

import main.crazyJava.crazyPojo.JavaReflection.JavaMethodEntity;
import main.crazyJava.crazyPojo.common.TechnologyEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;

@Repository
public class HibernateJdbcDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;



    @Transactional
    public TechnologyEntity getEntityById(String id) {
        //加载默认配置文件
        //Configuration config = new Configuration().configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        //创建会话工厂对象
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        // 创建Transaction实例

        Session session = sessionFactory.openSession();

        //Transaction tx = session.beginTransaction();

        TechnologyEntity entity = session.get(TechnologyEntity.class, id);
        session.close();
        return entity;
    }

    public void saveEntity(TechnologyEntity entity) {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        //创建会话工厂对象
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        // 创建Transaction实例
        Session session = sessionFactory.openSession();

        Transaction tx = session.beginTransaction();
        session.save(entity);
        tx.commit();
        session.close();
    }

    public <T> T getEntity(Class<T> clazz, String id) {
        return hibernateTemplate.get(clazz, id);
    }

    public void saveEntity(Object o) {
        hibernateTemplate.save(o);
    }

    public void update(Object o) {
        hibernateTemplate.update(o);
    }

    public void saveBatch(List<JavaMethodEntity> list,String rId) {
        hibernateTemplate.execute(session -> {
            session.doWork(conn -> {
                String sql = "INSERT INTO JAVA_METHOD (ID_,METHOD_NAME,EXPLAIN_,REFLECTION_ID) VALUES (?,?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                if (list.size() > 0) {
                    for (JavaMethodEntity entity : list) {
                        pstmt.setString(1, UUID.randomUUID().toString());
                        pstmt.setString(2, entity.getMethodName());
                        pstmt.setString(3, entity.getExplain());
                        pstmt.setString(4, rId);
                        pstmt.addBatch();
                    }
                }
                pstmt.executeBatch();
            });
            return null;
        });

    }
}
