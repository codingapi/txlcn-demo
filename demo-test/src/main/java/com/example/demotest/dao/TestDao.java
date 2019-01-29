package com.example.demotest.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class TestDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void truncateDemo(){
        jdbcTemplate.update("TRUNCATE TABLE t_demo");
    }

    public boolean isOk(){
        String sql = "select count(*) count ,app_name  FROM t_demo GROUP BY app_name FOR UPDATE";
        List<TableInfo> res =  jdbcTemplate.query(sql, new RowMapper<TableInfo>() {
             @Override
             public TableInfo mapRow(ResultSet resultSet, int i) throws SQLException {
                 TableInfo tableInfo = new TableInfo();
                 tableInfo.setAppName(resultSet.getString("app_name"));
                 tableInfo.setCount(resultSet.getInt("count"));
                 return tableInfo;
             }
         });
        if(res.size()>0&&res.size()==3) {
            return (res.get(0).getCount()==res.get(1).getCount())&&(res.get(0).getCount()==res.get(2).getCount());
        }
        return true;
    }


    static class TableInfo{
        private int count;
        private String appName;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }
    }
}
