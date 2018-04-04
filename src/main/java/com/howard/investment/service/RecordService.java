package com.howard.investment.service;



import java.util.List;
import java.util.Map;

/**
 * Created by Howard on 2017/11/23
 */

public interface RecordService {

	List<Map> getRecordByKey(Integer pageNum,String type);
	
	int getRecordByKeyCount(String type);
}
