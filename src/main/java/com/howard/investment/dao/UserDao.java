package com.howard.investment.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by WangHua on 18/3/29.
 */

@Mapper
public interface UserDao {

    Map getUserByKey(@Param("userName") String userName,@Param("password") String password);

    int updateUserByKey(@Param("obj") Map map);

    List<Map> getUserByBmdm(@Param("bmdm") String bmdm);

    List<Map> getUserByJu();
    


}