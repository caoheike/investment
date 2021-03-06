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
	public List<Map> getRecordByKey(Integer pageNum, String bz, String type,String typeid,String bmdm) {
		return recordDao.getRecordByKey(pageNum, bz, type,typeid,bmdm);
	}

	@Override
	public int getRecordByKeyCount(String bz, String type,String typeid,String bmdm) {
		return recordDao.getRecordByKeyCount(bz,type,typeid,bmdm);
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
	public int updateXmByJhztz() {
		return recordDao.updateXmByJhztz();
	}

	@Override
	public List<Map> getXmdwxxByKey(Integer pageNum, String bmdm, String dwmc) {
		return recordDao.getXmdwxxByKey(pageNum, bmdm, dwmc);
	}

	@Override
	public int getXmdwxxByKeyCount(String bmdm, String dwmc) {
		return recordDao.getXmdwxxByKeyCount(bmdm, dwmc);
	}

	@Override
	public int updateInfo(String id,String infostate) {
		return recordDao.updateInfo(id,infostate);
	}

	@Override
	public Map getDeptById(String id) {
		return recordDao.getDeptById(id);
	}

}
