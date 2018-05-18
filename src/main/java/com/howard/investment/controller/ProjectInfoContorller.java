package com.howard.investment.controller;



import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.howard.investment.service.impl.UserServiceImpl;
import com.howard.investment.tools.Tools;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.howard.bean.ProjectInfo;
import com.howard.bean.xmxx;
import com.howard.investment.service.ProjectInfoService;
import com.howard.investment.service.impl.ProjectInfoServiceImpl;



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
	@Autowired
	private UserServiceImpl userService;
		@ResponseBody
	    @RequestMapping(value = "queryProjectInfo",method = RequestMethod.POST)
	    public List<Map> queryProjectInfo(HttpServletRequest request,@RequestParam("projectStatus") String projectStatus,@RequestParam("deptid") String deptid,Model model) throws Exception {
	    	HttpSession sessionr=request.getSession();
	    	Map maps=(Map) sessionr.getAttribute("sessionUser");
			Map<String,Object> map= new HashMap<String,Object>();
			String xmmcs=request.getParameter("xmmcs");
			String frdws=request.getParameter("frdws");
			String bmdm=request.getParameter("bmdm");
			
			
	    	if(maps.get("typeid").toString().equals("2")){
	    		if(projectStatus.equals("3")){
	    			map.put("ifadmin", "no");
	    		}else{
	    			map.put("ifadmin", "yes");
	    		}
	  
	    	}else{
    		if(bmdm!=null&&!"".equals(bmdm)&&bmdm.equals("0")){
    	 	 	map.put("ifadmin", "no");
    		}else{
    	 	 	map.put("ifadmin", "no");
    		}
	    
	    	}
	    	
	    	if(xmmcs!=null&&!"".equals(xmmcs)){
	        	map.put("xmmcs","'%%"+xmmcs+"%%'");
	    	}
	    	if(frdws!=null&&!"".equals(frdws)){
	        	map.put("frdws", "'%%"+frdws+"%%'");
	    	}
	
	
	    	if(bmdm!=null&&!"".equals(bmdm)){
	        	map.put("bmdm", "'%%"+bmdm+"%%'");
	    		
	    	}
	
	    	map.put("projectStatus", projectStatus);
	    	map.put("deptid", Integer.parseInt(deptid));
	   
			List<Map> queryProjectInfo = projectinfoserviceimpl.queryProjectInfo(map);
	    	return queryProjectInfo;
	    }
	    
		  @RequestMapping(value = "info",method = RequestMethod.GET)
		    public String info(HttpServletRequest request,@RequestParam("status") String status,Model mode) throws Exception {
				HttpSession session=request.getSession();
		    	Map<String,Object> maps= (Map<String, Object>) session.getAttribute("sessionUser");
			  mode.addAttribute("deptid", maps.get("deptid"));
			  mode.addAttribute("typeid", maps.get("typeid"));
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
	       	Map <String,Object>queryProjectInfo = projectinfoserviceimpl.deptInfo(id);
	       	queryProjectInfo.put("type",type);
	       	String xxid=request.getParameter("xxid");
	       	queryProjectInfo.put("xxid",xxid);
	       	if(!queryProjectInfo.containsKey("file1")){
	       		queryProjectInfo.put("file1","");
	       	}
	       	if(!queryProjectInfo.containsKey("file2")){
	       		queryProjectInfo.put("file2","");
	       		
	       	}
	       	if(!queryProjectInfo.containsKey("file3")){
	       		queryProjectInfo.put("file3","");
	       		
	       	}
	       	if(!queryProjectInfo.containsKey("file4")){
	       		queryProjectInfo.put("file4","");
	       		
	       	}
	       	if(!queryProjectInfo.containsKey("file5")){
	       		queryProjectInfo.put("file5","");
	       		
	       	}
	       	if(!queryProjectInfo.containsKey("status")){
	       		queryProjectInfo.put("status","1");
	       	}
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
	    public Map<String,Object> sendMassage(HttpServletRequest request,@RequestParam("bmdm") String bmdm,@RequestParam("xmmc") String xmmc,@RequestParam("xmdwmc") String xmdwmc,@RequestParam("title") String title,@RequestParam("xmid")String  xmid) throws Exception {
			HttpSession session=request.getSession();
			Map maps=(Map) session.getAttribute("sessionUser");
			
	    	String fsz="统计局";
		
			Date day=new Date();    
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd"); 
	    	Map<String,Object> map=new HashMap<String,Object>();
	    	map.put("fsz", "统计局");
	    	if(maps.get("typeid").toString().equals("2")){
	    	  	map.put("xminfotype",2);
	    	}else if(maps.get("typeid").toString().equals("10")){
	    	  	map.put("xminfotype",10);
	    	}
	    	map.put("bmdm",Integer.parseInt(bmdm));
	    	map.put("xmmc",xmmc);
	    	map.put("xmdwmc",xmdwmc);
	    	map.put("title",title);
	    	map.put("inputdate",df.format(day));
	    	map.put("xmid",Integer.parseInt(xmid));
			List<Map> users = null;
			if(bmdm!=null && !"".equals(bmdm)){
				users = userService.getUserByBmdm(bmdm);

			}else{
				users = userService.getUserByJu();
			}
			for (int j = 0; j < users.size(); j++) {
				Tools.sendWxMsg(users.get(j).get("openid").toString(),xmmc,title);
			}
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
			  model.addAttribute("msgtype",2);
			  
		}else if(map.get("typeid").toString().equals("2")){//部门用户
			  model.addAttribute("msgtype",10);
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
		    public List<Map> juuserinfo(HttpServletRequest request) throws Exception {
			  	 
			  	//查询统计局发送给部门的消息//部门用户只能看到属于自己本门的
				  //查询客户给局用户发送的消息//这个不需要划分，只要是局用户可以查询所有消息
			  	HttpSession session=request.getSession();
			  	 List<Map> list=null;
			  	Map<String,Object> map=(Map<String, Object>) session.getAttribute("sessionUser");
			  		if(map.get("typeid").toString().equals("10")){
			  			//据用户
			  		  map.put("msgtype",2);
			  		 list=projectinfoserviceimpl.juuser(map);
			  		}else if(map.get("typeid").toString().equals("2")){
			  		  //获取deptid   部门用户
			  			//部门用户需要查询xmdm,根据用户的 deptid
			  			Map mapxmdm=projectinfoserviceimpl.getbmdm(Integer.parseInt(map.get("deptid").toString()));
			  		  map.put("msgtype",10);
			  		   map.put("bmdm", mapxmdm.get("code"));
			  		   list=projectinfoserviceimpl.juuser(map);
			  		}
			  	
			
				
				
				
		
		    	return list;
		    }
		  
		
		  @RequestMapping(value = "errorPage",method = RequestMethod.GET)
		    public String erroePage(HttpServletRequest request) throws Exception {
			  
			  
			  return "error";
		  }
		  
		  
		  /**
		   * 数据导入
		   */
		  
		
		  @RequestMapping(value = "infoimport",method = RequestMethod.POST)
		    public String infoimport(HttpServletRequest request,@RequestParam("file") MultipartFile file,Model model) throws Exception {
			  
			  
				List<ProjectInfo> list = new ArrayList<ProjectInfo>();
				List<ProjectInfo> lists = new ArrayList<ProjectInfo>();
				HSSFWorkbook book = new HSSFWorkbook(file.getInputStream());
			    HSSFSheet sheet = book.getSheetAt(0);
			    int count =0;
			    int successCount =0;
			    for(int i=2; i<sheet.getLastRowNum()+1; i++) {
			        HSSFRow row = sheet.getRow(i);
			        String bbyf = row.getCell(0).getStringCellValue(); 
			        if(row.getCell(0)!=null){
			        	  row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			        }
			        String xmfrm = row.getCell(1).getStringCellValue(); //url
			        Integer bmdm = (int) row.getCell(2).getNumericCellValue();
			        String xmdwmc = row.getCell(3).getStringCellValue();
			        String xmmc = row.getCell(4).getStringCellValue();
			        String frdm = row.getCell(5).getStringCellValue();
			        String jhztz = row.getCell(6).getStringCellValue();
			        String djzclxdm= row.getCell(7).getStringCellValue();
			        String hydm = row.getCell(8).getStringCellValue();
			        String lsgx = row.getCell(9).getStringCellValue();
			        String kgqk = row.getCell(10).getStringCellValue();
			        String jsxz = row.getCell(11).getStringCellValue();
			        String ljtz = row.getCell(12).getStringCellValue();
			        String bntz = row.getCell(13).getStringCellValue();
			        String bytz = row.getCell(14).getStringCellValue();
			        String jzgc = row.getCell(15).getStringCellValue();	
			        String azgz = row.getCell(16).getStringCellValue();
			        String sbgz = row.getCell(17).getStringCellValue();
			        String jtfy = row.getCell(18).getStringCellValue();
			        String tdgzf = row.getCell(19).getStringCellValue();
			        String jstz = row.getCell(20).getStringCellValue();
			        
			        String year=bbyf.substring(0, 4);
			        String mouth=bbyf.substring(4,bbyf.length());

			        if(mouth.contains("0")){
			        mouth= bbyf.substring(5,bbyf.length());
			        }else{
			        mouth=bbyf.substring(4,bbyf.length());
			        }
			        if(mouth.contains("0")){
			        mouth= bbyf.substring(5,bbyf.length());
			        }else{
			        mouth=bbyf.substring(4,bbyf.length());
			        }
			        Map map=projectinfoserviceimpl.queryCount(xmfrm);
			    
			        if(map!=null){
			        	list.add(new ProjectInfo(map.get("id").toString(),map.get("deptid").toString(),bmdm,year,mouth,djzclxdm,hydm,lsgx,kgqk,jsxz,ljtz,bntz,bytz,jzgc,azgz,sbgz,jtfy,tdgzf,jstz,xmdwmc,xmmc,frdm,jhztz));
			        	successCount++;
			        	}else{
			        	
			        		
			        		lists.add(new ProjectInfo("","",bmdm,year,mouth,djzclxdm,hydm,lsgx,kgqk,jsxz,ljtz,bntz,bytz,jzgc,azgz,sbgz,jtfy,tdgzf,jstz,xmdwmc,xmmc,frdm,jhztz));
			            	count++;
			        	}
			    
			    	
			   
			    }
			    	
			    	for (int i = 0; i < list.size(); i++) {
			    	int ct=projectinfoserviceimpl.inportInfo(list.get(i));
			    	if(ct!=0){
			    	System.out.println("插入成功"+i);	
			    	}
					}
			    	System.out.println(count+"条无法插入，原因库中不存在");
			    	if(count!=0){
			    		model.addAttribute("status","yes");
			    		model.addAttribute("list",lists);
			    		model.addAttribute("count",count);
			   
			    	}else{
			    		model.addAttribute("status","no");
			    	}
			    	model.addAttribute("successCount",successCount);
			    	model.addAttribute("type","0");
			 
			    	 return "import";
			    	
			    	 
			  
		  }
		  
		  	@RequestMapping(value = "imports",method = RequestMethod.GET)
		    public String imports(HttpServletRequest request,Model model) throws Exception {
			  model.addAttribute("status","no");
			  
			  
			  return "import";
			  
		  }
	  
		    @RequestMapping(value = "infoimports",method = RequestMethod.POST)
		    public String infoimports(HttpServletRequest request,@RequestParam("file") MultipartFile file,Model model) throws Exception {
			  
			  	int successCount=0;
				List<xmxx> list = new ArrayList<xmxx>();
				List<xmxx> lists = new ArrayList<xmxx>();
				HSSFWorkbook book = new HSSFWorkbook(file.getInputStream());
			    HSSFSheet sheet = book.getSheetAt(0);
			    int count =0;
			    for(int i=2; i<sheet.getLastRowNum()+1; i++) {
			        HSSFRow row = sheet.getRow(i);
			 
			       if(row.getCell(0)!=null){
			    	   row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			       }
			       String xmfrm=  row.getCell(0).getStringCellValue();
			       int bmdm=  (int)row.getCell(1).getNumericCellValue();
			       if(row.getCell(1)!=null){
			    	   row.getCell(1).setCellType(Cell.CELL_TYPE_NUMERIC);
			       }
			       //根据部门代码去查询 deptid啦
			       Map mapinfo=projectinfoserviceimpl.querydept(bmdm);
			       
			       String xmdwmc=  row.getCell(2).getStringCellValue();
			       String xmmc=  row.getCell(3).getStringCellValue();
			       String frdm=  row.getCell(4).getStringCellValue();
			       if(row.getCell(5)!=null){
			    	   row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
			       }
			       String  jhztz1= row.getCell(5).getStringCellValue();
			       int  jhztz=0;
			       int xmfl=  (int)row.getCell(6).getNumericCellValue();
			     
//			        Map map=projectinfoserviceimpl.queryCount(xmfrm.toString());
//			    
			     
//					if(map!=null){
			       	if(mapinfo!=null&&mapinfo.get("deptid")!=""){
			       		//查询项目再库中是否存在
			       		int frmcount=projectinfoserviceimpl.xmfrCount(xmfrm);
			       		if(frmcount<=0){
			       	  		list.add(new xmxx(Integer.parseInt(mapinfo.get("id").toString()),xmfrm, bmdm, xmdwmc, xmmc, frdm,jhztz, xmfl));
			       		}else{
			       			
			       		}
			     
			       	}

			    
			    	
			   
			    }
			    	
			    	for (int i = 0; i < list.size(); i++) {
			    	int ct=projectinfoserviceimpl.inportInfos(list.get(i));
			    	if(ct!=0){
			    		successCount++;
			    	System.out.println("插入成功"+i);	
			    	}
					}
			    	System.out.println(count+"条无法插入，原因库中不存在");
			    	if(count!=0){
			    		model.addAttribute("status","yes");
			    		model.addAttribute("list",lists);
			    		model.addAttribute("count",count);
			    	
			    	}else{
			    		model.addAttribute("status","no");
			    	}
			    	model.addAttribute("successCount",successCount);
			    	model.addAttribute("type","1");
			    	 return "import";
			    	
			  
		  }	  
		    
		    /**
		     * 项目认领
		     */
		    @ResponseBody
		    @RequestMapping(value = "xmrls",method = RequestMethod.POST)
		    public Map xmrl(HttpServletRequest request,@RequestParam("id") String id) throws Exception {
		    	//首先拿到拿钱部门代码 根据deptid
		    	HttpSession session=request.getSession();
		    	Map map=(Map) session.getAttribute("sessionUser");
		    	String deptid =map.get("deptid").toString();
		    	
		    	//得到部门信息
		    	
		    	Map mapdept=projectinfoserviceimpl.getbmdm(Integer.parseInt(deptid));
		    	String bmdm=mapdept.get("code").toString();
		    	//拿到本人呢对部门代码去修改项目的
		    	map.put("bmdm",bmdm);
		    	map.put("id",id);
		    	map.put("xmfl","2");
		    	int row=projectinfoserviceimpl.updateBmdm(map);
		    	if(row!=0){
		    		map.put("restInfo","success");
		    	}else{
		    		map.put("restInfo","error");
		    	}
		    	
		    	
				return map;
		    
		    
		    
		    }
		    /**
		     * 项目转出
		     */
		    
		    @ResponseBody
		    @RequestMapping(value = "xmzc",method = RequestMethod.POST)
		    public Map xmzc(HttpServletRequest request,@RequestParam("id") String id) throws Exception {
		    	//首先拿到拿钱部门代码 根据deptid
		    	HttpSession session=request.getSession();
		    	Map map=(Map) session.getAttribute("sessionUser");
		    	String deptid =map.get("deptid").toString();
		    	
		    	//得到部门信息
		    	
		    	Map mapdept=projectinfoserviceimpl.getbmdm(Integer.parseInt(deptid));
		    	String bmdm=mapdept.get("code").toString();
		    	//拿到本人呢对部门代码去修改项目的
		    	map.put("bmdm","");
		    	map.put("id",id);
		    	map.put("xmfl","3");
		    	int row=projectinfoserviceimpl.updateBmdm(map);
		    	if(row!=0){
		    		map.put("restInfo","success");
		    	}else{
		    		map.put("restInfo","error");
		    	}
		    	
		    	
				return map;
		    
		    
		    
		    }
	/**
	 *  查询数据报表页面
	 */

	@RequestMapping(value = "getData",method = RequestMethod.GET)
	public String getData(HttpServletRequest request) throws Exception {


		return "getData";
	}
	/**
	 * 根据提交的数据 去网站爬取
	 */
	@ResponseBody
	@RequestMapping(value = "reptileData",method = RequestMethod.POST)
	public String reptileData(HttpServletRequest request) throws Exception {
		String  xmzt=request.getParameter("xmzt");
		String  bm=request.getParameter("bm");
		String  y=request.getParameter("y");
		String  m=request.getParameter("m");
		String  xmxx=request.getParameter("xmxx");

		WebClient WebClients = new WebClient();
		HtmlPage page = WebClients.getPage("http://www.hyxwqy.com/qytj/login.asp");
		HtmlTextInput username = page.getElementByName("username");
		HtmlPasswordInput username2 = page.getElementByName("password");
		username.setValueAttribute("tjjfl");
		username2.setValueAttribute("111111");
		page.executeJavaScript("javascript:chkLoginFrm()");
//		Thread.sleep(3000);
//		HtmlPage pageInfo=
		WebClients.getPage("http://www.hyxwqy.com/qytj/tzxmsj.asp");//进入查询页面	HtmlSelect xmfl = pageInfo.getElementByName("xmfl");

		StringBuffer sb=new StringBuffer();
		HtmlPage pages= null;
		pages = WebClients.getPage("http://www.hyxwqy.com/qytj/tzxmsj.asp?action=search&stype=xmsj&xmfl="+xmzt+"&stypes=2&deptid="+bm+"&y="+y+"&m="+m+"&keywords="+xmxx+"&submit=搜+索&page="+1+"");
		HtmlTable table= (HtmlTable) pages.getByXPath("/html/body/div[1]/div[4]/div/table").get(0);

//		for (int i = 1; i <20 ; i++) {
//
//
//			try {
//				pages = WebClients.getPage("http://www.hyxwqy.com/qytj/tzxmsj.asp?action=search&stype=xmsj&xmfl="+xmzt+"&stypes=2&deptid="+bm+"&y="+y+"&m="+m+"&keywords="+xmxx+"&submit=搜+索&page="+i+"");
//				HtmlTable table= (HtmlTable) pages.getByXPath("/html/body/div[1]/div[4]/div/table").get(0);
//				if(table.asXml().toString().contains("下一页")){
//					sb.append(table.asXml());
//				}else{
//					break;
//				}
//
//			} catch (Exception e) {
//				break;
//			}
//
//		}
		for (int i = 0; i <table.getRowCount() ; i++) {
			if(table.getRow(i).asText().contains("页")){
				table.getRow(i).remove();
			}

		}


		return table.asXml().replaceAll("1155","100%");
	}




}
