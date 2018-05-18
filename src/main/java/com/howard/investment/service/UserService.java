package com.howard.investment.service;



import java.util.List;
import java.util.Map;

/**
 * Created by Howard on 2017/11/23
 */

public interface UserService {

	Map getUserByKey(String userName,String password);

	int updateUserByKey(Map map);

	List<Map> getUserByBmdm(String bmdm);

	List<Map> getUserByJu();


}
