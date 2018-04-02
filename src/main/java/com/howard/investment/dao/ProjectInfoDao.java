package com.howard.investment.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 
* @ClassName: ProjectInfoDao  
* @Description: TODO(这里用一句话描述这个类的作用)  
* @Created by: biyyoung 
* @date 2018年3月29日  
*
 */

@Mapper
public interface ProjectInfoDao {

    List<Map> queryProjectInfo(Map map);
    
    int deleteProjectInfo(String projectId);
    
    List<Map>  queryDeptInfo();
    
    Map deptInfo(String id);
    
    int updataInfo(Map map);
    int updateInfos(Map map);
    int updatexiaoxi(Map map);
    
    int sendMessage(Map map);
    
    List<Map>  juuser(Map map);



}