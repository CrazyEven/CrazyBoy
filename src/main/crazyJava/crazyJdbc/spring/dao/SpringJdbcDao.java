package main.crazyJava.crazyJdbc.spring.dao;

import main.crazyJava.carzyPojo.common.TechnologyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * 测试DAO
 */
@Repository //声明注册
public class SpringJdbcDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public TechnologyEntity getTechnologyEntity(String id) {

        String sql = "SELECT * FROM CRAZY_TECHNOLOGY WHERE ID = ?";

        final TechnologyEntity technologyEntity = new TechnologyEntity();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jdbcTemplate.query(sql, new Object[]{id}, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                technologyEntity.setId(resultSet.getString("ID"));
                technologyEntity.setName(resultSet.getString("NAME_"));
                technologyEntity.setRemark(resultSet.getString("REMARK_"));
                try {
                    technologyEntity.setCreateTime(sdf.parse(resultSet.getString("CREATE_TIME")));
                    technologyEntity.setUpdateTime(sdf.parse(resultSet.getString("UPDATE_TIME")));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return technologyEntity;


    }
}
