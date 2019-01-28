package com.codingapi.example.demod;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 * Description:
 * Date: 19-1-22 下午4:19
 *
 * @author ujued
 */
@Mapper
public interface MoreOperateMapper {

    @Update("update t_demo2 set demo=demo+1, update_time=#{date}")
    void update(Date date);

    @Delete("delete from t_demo3 where id = 2")
    void delete();

}
