package com.codingapi.example.common.db.mapper;

import com.codingapi.example.common.db.domain.Demo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * Description:
 * Date: 2018/12/25
 *
 * @author ujued
 */
@Mapper
public interface DemoMapper {

    @Insert("insert into t_demo(demo_field, group_id, unit_id, create_time,app_name) values(#{demoField}, #{groupId}, #{unitId}, #{createTime},#{appName})")
    void save(Demo demo);

    @Delete("delete from t_demo where id=#{id}")
    void deleteById(Long id);


    @Delete("delete from t_demo where id >= 4")
    void delete5();


    @Update("update t_demo set app_name = #{name} where id = 3")
    void update(String name);
}
