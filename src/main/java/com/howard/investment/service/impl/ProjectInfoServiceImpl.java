package com.howard.investment.service.impl;

import com.howard.investment.dao.ProjectInfoDao;
import com.howard.investment.dao.UserDao;
import com.howard.investment.service.ProjectInfoService;
import com.howard.investment.service.UserService;

import com.mysql.jdbc.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 
* @ClassName: ProjectInfoServiceImpl  
* @Description: TODO(这里用一句话描述这个类的作用)  
* @Created by: biyyoung 
* @date 2018年3月29日  
*
 */
@Service
public  class ProjectInfoServiceImpl implements ProjectInfoService {
	static Connection connect = null;  
    static PreparedStatement stmt = null;  
    static ResultSet rs = null;  
    @Autowired
    private ProjectInfoDao projectinfodao;

	@Override
	public List<Map> queryProjectInfo(Map map) {
		
		return projectinfodao.queryProjectInfo(map);
	}
	@Override
	public int deleteProjectInfo(String projectId) {
		
		return projectinfodao.deleteProjectInfo(projectId);
	}
	@Override
	public     List<Map>   queryDeptInfo() {
		
		return projectinfodao.queryDeptInfo();
	}

	@Override
	public Map deptInfo(String id) {
		// TODO Auto-generated method stub
		return projectinfodao.deptInfo(id);
	}
	
	@Override
	public int updateInfo(Map map) {
		// TODO Auto-generated method stub

		return projectinfodao.updataInfo(map);
	}
	
	@Override
	public int updateInfos(Map map) {
		// TODO Auto-generated method stub

		return projectinfodao.updateInfos(map);
	}
	
	@Override
	public int updatexiaoxi(Map map) {
		// TODO Auto-generated method stub

		return projectinfodao.updatexiaoxi(map);
	}
	
	@Override
	public int sendMessage(Map map) {

     
		return projectinfodao.sendMessage(map);
	}
	@Override
	public List<Map>  juuser(Map map) {

     
		return projectinfodao.juuser(map);
	}
	
	/**
	 * 查询部门代码
	 */
	public Map getbmdm(int deptid) {

	     
		return projectinfodao.getbmdm(deptid);
	}
 
}
