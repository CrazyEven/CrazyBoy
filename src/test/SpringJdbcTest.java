package test;

import main.crazyJava.carzyPojo.common.TechnologyEntity;
import main.crazyJava.crazyJdbc.spring.dao.SpringJdbcDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SpringJdbcTest {
    @Autowired
    private SpringJdbcDao springJdbcDao;

    @Test
    public void test(){
        TechnologyEntity technologyEntity = springJdbcDao.getTechnologyEntity("crazy_001");
        System.out.println(technologyEntity.getId());
        System.out.println(technologyEntity.getName());
        System.out.println(technologyEntity.getRemark());
        System.out.println(technologyEntity.getCreateTime());
        System.out.println(technologyEntity.getUpdateTime());
    }
}
