package com.howard.investment.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by WangHua on 18/3/29.
 */

@Mapper
public interface RecordDao {

	List<Map> getRecordByKey(@Param("pageNum") Integer pageNum,@Param("type") String type);
	
	int deleteXmById(@Param("id") String id);
	
	List<Map> getDept();
	
	List<Map> getXmbaxxByJhztz();
	
	int insertXmxx(@Param("obj") Map map);
	
	List<Map> getXmByIds(@Param("ids") String ids);
	
	List<Map> getXmbaxxByDeptBmdm(@Param("bmdm") String  bmdm);
	
	int updateXmByKey(@Param("obj") Map map);
	
	int deleteXmByJhztz();
	
	int getRecordByKeyCount(@Param("type") String type);
	
	List<Map> getXmfrdwAll();
	
	int updateByKey(@Param("bmdm") String bmdm,@Param("xmfrdw") String xmfrdw);
	
	List<Map> getXminfoByKey(@Param("deptId") String deptId,@Param("typeId") int typeId);

}