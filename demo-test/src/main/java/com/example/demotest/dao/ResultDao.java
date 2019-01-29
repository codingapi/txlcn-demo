package com.example.demotest.dao;

import com.example.demotest.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ResultDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init(){
        jdbcTemplate.update(
                "CREATE TABLE IF NOT  EXISTS `t_result`  (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,\n" +
                "  `model` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,\n" +
                "\t`is_ok` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,\n" +
                "  `create_time` datetime(0) NULL DEFAULT NULL,\n" +
                "\t`sample` varchar(32)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,\n" +
                "\t`ko` varchar(32)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,\n" +
                "\t`error` varchar(32)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,\n" +
                "\t`average` varchar(32)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,\n" +
                "\t`min` varchar(32)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,\n" +
                "\t`max` varchar(32)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,\n" +
                "\t`pct_90th` varchar(32)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,\n" +
                "\t`pct_95th` varchar(32)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,\n" +
                "\t`pct_99th` varchar(32)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,\n" +
                "\t`throughput` varchar(32)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,\n" +
                "\t`received` varchar(32)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,\n" +
                "\t`sent` varchar(32)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`) USING BTREE\n" +
                ") ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic");
    }



    public void save(Result result){
        String sql = "insert into t_result(type,model,is_ok,create_time,sample,ko,error,average,min,max,pct_90th,pct_95th,pct_99th,throughput,received,sent) " +
                "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        jdbcTemplate.update(sql,result.getType(),result.getModel(),result.getIs_ok(),result.getCreate_time(),result.getSample(),result.getKo(),result.getError(),result.getAverage(),result.getMin(),result.getMax(),result.getPct_90th(),result.getPct_95th(),result.getPct_99th(),result.getThroughput(),result.getReceived(),result.getSent());
    }

    public void truncate() {
        jdbcTemplate.update("TRUNCATE TABLE t_result");
    }
}
