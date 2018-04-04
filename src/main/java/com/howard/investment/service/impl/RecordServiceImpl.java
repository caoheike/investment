package com.howard.investment.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.howard.investment.dao.RecordDao;
import com.howard.investment.service.RecordService;

/**
 * Created by Howard on 2017/11/23
 */

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordDao recordDao;

	@Override
	public List<Map> getRecordByKey(Integer pageNum, String type) {
		return recordDao.getRecordByKey(pageNum, type);
	}

	@Override
	public int getRecordByKeyCount(String type) {
		return recordDao.getRecordByKeyCount(type);
	}

}
