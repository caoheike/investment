package com.howard.investment.service;



import java.util.List;
import java.util.Map;


public interface ProjectInfoService {



	List<Map> queryProjectInfo(Map map);

	/**  
	* @Title: deleteProjectInfo  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param @param projectId
	* @param @return    参数  
	* @return int    返回类型  
	* @throws  
	*/  
	int deleteProjectInfo(String projectId);

	List<Map> queryDeptInfo();
	
//	List<Map> deptInfo();

	Map deptInfo(String id);

	/**  
	* @Title: updateInfo  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param @param id
	* @param @return    参数  
	* @return int    返回类型  
	* @throws  
	*/  


	/**  
	* @Title: updateInfo  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param @param id
	* @param @param file1
	* @param @param file2
	* @param @param file3
	* @param @param file4
	* @param @param file5
	* @param @return    参数  
	* @return int    返回类型  
	* @throws  
	*/  
	int updateInfo(Map map);
	
	int updateInfos(Map map);

	/**  
	* @Title: sendMessage  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param @param map
	* @param @return    参数  
	* @return int    返回类型  
	* @throws  
	*/  


	/**  
	* @Title: sendMessage  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param @param map
	* @param @return    参数  
	* @return int    返回类型  
	* @throws  
	*/  
	 int sendMessage(Map map);
	 List<Map>  juuser(Map map);
	 int updatexiaoxi(Map map);
	
    
}
