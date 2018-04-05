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
	
	@Override
	public int updateByKey(String bmdm,String xmfrdw) {
		return recordDao.updateByKey(bmdm,xmfrdw);
	}
	@Override
	public List<Map> getXmfrdwAll() {
		return recordDao.getXmfrdwAll();
	}

	@Override
	public List<Map> getXminfoByKey(String deptId,int typeId) {
		return recordDao.getXminfoByKey(deptId,typeId);
	}

	@Override
	public int deleteXmById(String id) {
		return recordDao.deleteXmById(id);
	}

	@Override
	public int updateXmByKey(Map map) {
		return recordDao.updateXmByKey(map);
	}

	@Override
	public List<Map> getDept(){
		return recordDao.getDept();
	}

	@Override
	public List<Map> getXmbaxxByDeptBmdm(String bmdm) {
		return recordDao.getXmbaxxByDeptBmdm(bmdm);
	}

	@Override
	public List<Map> getXmByIds(String ids) {
		return recordDao.getXmByIds(ids);
	}

	@Override
	public List<Map> getXmbaxxByJhztz() {
		return recordDao.getXmbaxxByJhztz();
	}

	@Override
	public int insertXmxx(Map map) {
		return recordDao.insertXmxx(map);
	}

	@Override
	public int deleteXmByJhztz() {
		return recordDao.deleteXmByJhztz();
	}

}
