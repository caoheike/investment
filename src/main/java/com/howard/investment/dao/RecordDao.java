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
public interface RecordDao {

	List<Map> getRecordByKey(@Param("pageNum") Integer pageNum,@Param("type") String type);
	
	int getRecordByKeyCount(@Param("type") String type);
	
	int matchDept();
	
	List<Map> getXminfoByKey(@Param("deptId") String deptId,@Param("typeId") int typeId);
    


}