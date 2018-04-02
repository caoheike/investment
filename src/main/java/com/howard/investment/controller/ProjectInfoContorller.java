package com.howard.investment.controller;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.howard.investment.service.ProjectInfoService;
import com.howard.investment.service.impl.ProjectInfoServiceImpl;
import com.howard.investment.tools.DBConnection;


//注视添加

/**
 *@Title: 项目信息管理
 *@Description:  项目基本信息查询以及操作
 *@Author:BigYoung  
 *@Since:2018年3月29日  
 *@Version:1.1.0
 */
@Controller
@RequestMapping("/")
public class ProjectInfoContorller {
    static Connection connect = null;  
    static PreparedStatement stmt = null;  
    static ResultSet rs = null;  
    @Autowired
    private ProjectInfoServiceImpl projectinfoserviceimpl;
		@ResponseBody
	    @RequestMapping(value = "queryProjectInfo",method = RequestMethod.POST)
	    public List<Map> queryProjectInfo(HttpServletRequest request,@RequestParam("projectStatus") String projectStatus,@RequestParam("deptid") String deptid,Model model) throws Exception {
	    	Map<String,Object> map= new HashMap<String,Object>();
	    	map.put("projectStatus", projectStatus);
	    	map.put("deptid", deptid);
			List<Map> queryProjectInfo = projectinfoserviceimpl.queryProjectInfo(map);
	    	return queryProjectInfo;
	    }
	    
		  @RequestMapping(value = "info",method = RequestMethod.GET)
		    public String info(HttpServletRequest request,@RequestParam("status") String status,Model mode) throws Exception {
				HttpSession session=request.getSession();
		    	Map<String,Object> maps= (Map<String, Object>) session.getAttribute("sessionUser");
			  mode.addAttribute("deptid", maps.get("deptid"));
			  mode.addAttribute("status", status);
		    	return "main_list";
		    }
	    
	    
	    /**
	     * 
	    * @Title: deleteProjectInfo  
	    * @Description: TODO(删除项目信息)  
	    * @param @param request
	    * @param @param projectStatus
	    * @param @param model
	    * @param @return
	    * @param @throws Exception    参数  
	    * @return String    返回类型  
	    * @throws
	     */
	    @ResponseBody
	    @RequestMapping(value = "deleteProjectInfo",method = RequestMethod.POST)
	    public Map<String,Object> deleteProjectInfo(HttpServletRequest request,@RequestParam("projectId") String projectId) throws Exception {
	    	Map<String,Object> map= new HashMap<String,Object>();
	    	
	    	int row=projectinfoserviceimpl.deleteProjectInfo(projectId);
	    	if(row!=0){
	    		map.put("restInfo","success");
	    	}else{
	    		map.put("restInfo","fault");
	    	}
	    
	    	return map;
	    }
	    /**
	     * 部门查询
	     */
	    
	    
	    @ResponseBody
	    @RequestMapping(value = "queryDeptInfo",method = RequestMethod.POST)
	    public List<Map> queryDeptInfo(HttpServletRequest request) throws Exception {
	    	Map<String,Object> map= new HashMap<String,Object>();
	    	
	    	List<Map> queryDeptInfo = projectinfoserviceimpl.queryDeptInfo();
	    
	    
	    	return  queryDeptInfo;
	    }
	    /**
	     * 跳转编辑页面
	     * 按照id查询详情
	     */
	
	    @RequestMapping(value = "deptInfo",method = RequestMethod.GET)
	    public String deptInfo(HttpServletRequest request,@RequestParam("id") String id,Model model,@RequestParam("type") String type) throws Exception {
	       	Map queryProjectInfo = projectinfoserviceimpl.deptInfo(id);
	       	queryProjectInfo.put("type",type);
	       	String xxid=request.getParameter("xxid");
	       	queryProjectInfo.put("xxid",xxid);
       	model.addAttribute("info", queryProjectInfo);
       	
       	//根据taype走不同页面
       
	    
	    	return  "main_info";
	    }
	    /**
	     * 修改file路径 根据file路径 判断用户是否上传
	     */
//	    @RequestMapping(value = "updataInfo",method = RequestMethod.GET)
//	    public String updataInfo(HttpServletRequest request,@RequestParam("id") String id,Model model) throws Exception {
//	    	
//	       	Map queryProjectInfo = projectinfoserviceimpl.deptInfo(id);
//       	model.addAttribute("info", queryProjectInfo);
//	    
//	    	return  "main_info";
//	    }
	    
	    /**
	     * 
	    * @Title: messageAdd  
	    * @Description: TODO(这里用一句话描述这个方法的作用)  
	    * @param @param request
	    * @param @param fsz  统计局
	    * @param @param infotype  对应 admin type为10到用户
	    * @param @param bmdm 部门信息
	    * @param @param xmmc 项目信息到值
	    * @param @param xmdwmc 项目信息到值
	    * @param @param title 项目信息到值就内容
	    * @param @param inputdate 当前系统时间
	    * @param @param readdate 阅读时间
	    * @param @param infodate 默认为0
	    * @param @return
	    * @param @throws Exception    参数  
	    * @return String    返回类型  
	    * @throws
	     */
	    @ResponseBody
	    @RequestMapping(value = "sendMassage",method = RequestMethod.POST)
	    public Map<String,Object> sendMassage(HttpServletRequest request,@RequestParam("bmdm") String bmdm,@RequestParam("xmmc") String xmmc,@RequestParam("xmdwmc") String xmdwmc,@RequestParam("title") String title,@RequestParam("xmid")String  xmid,@RequestParam("msgtype") String msgtype) throws Exception {
			String fsz="统计局";
		
			Date day=new Date();    
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd"); 
	    	Map<String,Object> map=new HashMap<String,Object>();
	    	map.put("fsz", "统计局");
	    	if(msgtype.equals("10")){
	    	  	map.put("xminfotype",10);
	    	}else{
	    	  	map.put("xminfotype",0);
	    	}
	  
	    	map.put("bmdm",Integer.parseInt(bmdm));
	    	map.put("xmmc",xmmc);
	    	map.put("xmdwmc",xmdwmc);
	    	map.put("title",title);
	    	map.put("inputdate",df.format(day));
	    	map.put("xmid",Integer.parseInt(xmid));
	    	
	    int row= projectinfoserviceimpl.sendMessage(map);
		if(row!=0){
    		map.put("restInfo","success");
    	}else{
    		map.put("restInfo","fault");
    	}
    
	    	
	    	return  map;
	    }
	    /**
	     * 跳转局用户审批
	     */
	    
		  @RequestMapping(value = "juuser",method = RequestMethod.GET)
		    public String juuser(HttpServletRequest request,Model model) throws Exception {
		
		HttpSession session=request.getSession();
		Map<String,Object> map=(Map<String, Object>) session.getAttribute("sessionUser");
		if(map.get("typeid").toString().equals("10")){//据用户
			  model.addAttribute("msgtype",model.addAttribute("msgtype",map.get("typeid").toString()));
			
		}else if(map.get("typeid").toString().equals("2")){//部门用户
			  model.addAttribute("msgtype",model.addAttribute("msgtype", map.get("typeid").toString()));
		}
		
		
		
	
//			  
			  
		    	return "juuser";
		    }	
		  
		  /**
		   * 据用户查询消息
		  * @Title: juuser  
		  * @Description: TODO(这里用一句话描述这个方法的作用)  
		  * @param @param request
		  * @param @return
		  * @param @throws Exception    参数  
		  * @return String    返回类型  
		  * @throws
		   */
		  @ResponseBody
		  @RequestMapping(value = "juuserinfo",method = RequestMethod.POST)
		    public List<Map> juuserinfo(HttpServletRequest request,@RequestParam("msgtype") String msgtype) throws Exception {
			  	//查询统计局发送给部门的消息//部门用户只能看到属于自己本门的
				  //查询客户给局用户发送的消息//这个不需要划分，只要是局用户可以查询所有消息
				  Map<String,Object> map=new HashMap<String,Object>();
				  map.put("msgtype",Integer.parseInt(msgtype));
				  //获取deptid  
				    String deptid=request.getParameter("bmdm");
				   map.put("bmdm", Integer.parseInt(deptid));
				  List<Map> list=projectinfoserviceimpl.juuser(map);
		
		    	return list;
		    }
		  
		  
		  
	  
}
