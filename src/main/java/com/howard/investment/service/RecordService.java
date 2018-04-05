package com.howard.investment.service;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * Created by Howard on 2017/11/23
 */

public interface RecordService {

	List<Map> getRecordByKey(Integer pageNum,String type);
	
	int getRecordByKeyCount(String type);
	
	int deleteXmById(String id);
	
	List<Map> getDept();
	
	List<Map> getXmbaxxByJhztz();
	
	List<Map> getXmByIds(String ids);
	
	List<Map> getXmbaxxByDeptBmdm(String  bmdm);
	
	int insertXmxx(Map map);
	
	int deleteXmByJhztz();
	
	int updateXmByKey(Map map);
	
	List<Map> getXmfrdwAll();
	
	int updateByKey(String bmdm,String xmfrdw);
	
	List<Map> getXminfoByKey(String deptId,int typeId);
}
