package main.crazyJava.crazyJdbc.hibernate;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class HibernateHqlTemplate {

    /**
     * 目的是为了灵活的传入参数
     * 对于Serializable接口
     * java的包装类型 （Integer,Long,String,Double...）几乎都实现了这个接口.
     * @param tClass
     * @param id
     * @param <T>
     * @return
     */
    public <T> T getEntity(Class<T> tClass,Serializable id){

        return null
    }

}
