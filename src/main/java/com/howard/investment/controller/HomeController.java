package com.howard.investment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.howard.investment.service.impl.RecordServiceImpl;
import com.howard.investment.service.impl.UserServiceImpl;
import com.howard.investment.tools.Tools;

/**
 * Created by WangHua on 18/3/28.
 */
@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
    private UserServiceImpl userService;
	@Autowired
    private RecordServiceImpl recordService;

    @ResponseBody
    @RequestMapping("login")
    public Map<String,Object> login(@RequestParam String userName,@RequestParam String password,HttpSession session){
    	Map<String,Object> data=new HashMap<>();
    	password=Tools.encrypt16(password);
    	Map map=userService.getUserByKey(userName, password);
    	if(map!=null){
    		session.setAttribute("sessionUser", map);
    		data.put("flag", true);
    	}else{
    		data.put("flag", false);
    		data.put("msg", "帐号密码错误");
    	}
        return data;
    }
    
    @ResponseBody
    @RequestMapping("cancel")
    public Map<String,Object> cancel(HttpSession session){
    	Map<String,Object> data=new HashMap<>();
    	Object sessionUser=session.getAttribute("sessionUser");
    	if(sessionUser!=null){
    		session.setAttribute("sessionUser", null);
    		data.put("flag", true);
    	}else{
    		data.put("msg", "登录超时！");
    		data.put("flag", false);
    	}
        return data;
    }
    
    @ResponseBody
    @RequestMapping("matchDept")
    public Map<String,Object> matchDept(){
    	Map<String,Object> data=new HashMap<>();
    	/*recordService
    	if(sessionUser!=null){
    		
    		data.put("flag", true);
    	}else{
    		data.put("msg", "登录超时！");
    		data.put("flag", false);
    	}*/
        return data;
    }
    
    @RequestMapping("login.html")
    public String loginView(HttpSession session){
        return "login";
    }
    
    @RequestMapping("record.html")
    public String recordView(@RequestParam(required=false) Integer pageNum,@RequestParam(required=false) String type,Model model){
    	if(pageNum==null || pageNum<1){
    		model.addAttribute("pageNum", 1);
    		pageNum=15;
    	}else{
    		model.addAttribute("pageNum", pageNum);
    		pageNum*=15;
    	}
    	List<Map> list=recordService.getRecordByKey(pageNum, type);
    	int totalCount=recordService.getRecordByKeyCount(type);
    	model.addAttribute("list", list);
    	model.addAttribute("totalCount", totalCount);
    	model.addAttribute("pageCount", totalCount % 15 == 0 ? totalCount / 15: totalCount / 15 + 1);
    	model.addAttribute("pageSize", "15");
    	model.addAttribute("key", "?type="+type+"&pageNum=");
        return "record";
    }
    
    @RequestMapping("index.html")
    public String index(HttpSession session,Model model){
    	Object sessionUser=session.getAttribute("sessionUser");
    	if(sessionUser==null){
    		return "login";
    	}
    	Map map=(Map)sessionUser;
    	model.addAttribute("userName", map.get("username").toString());
    	if("10".equals(map.get("typeid").toString())){
    		model.addAttribute("userLevel", "局用户");
    	}else if("2".equals(map.get("typeid").toString())){
    		model.addAttribute("userLevel", "部门用户");
    	}else if("0".equals(map.get("typeid").toString())){
    		model.addAttribute("userLevel", "超级管理员");
    	}
    	
        return "index";
    }
    @ResponseBody
    @RequestMapping("userinfo")
    public Map<String,Object> userinfo(HttpSession session){
    	Map<String,Object> data=new HashMap<>();
    	Object sessionUser=session.getAttribute("sessionUser");
    	if(sessionUser==null){
    		data.put("flag", false);
    		return data;
    	}
    	Map map=(Map)sessionUser;
    	data.put("userName", map.get("username").toString());
    	if("10".equals(map.get("typeid").toString())){
    		data.put("userLevel", "局用户");
    	}else if("2".equals(map.get("typeid").toString())){
    		data.put("userLevel", "部门用户");
    	}else if("0".equals(map.get("typeid").toString())){
    		data.put("userLevel", "超级管理员");
    	}
    	data.put("typeId", map.get("typeid").toString());
    	data.put("flag", true);
        return data;
    }
    @ResponseBody
    @RequestMapping("getxminfo")
    public Map<String,Object> getxminfo(HttpSession session){
    	Map<String,Object> data=new HashMap<>();
    	Object sessionUser=session.getAttribute("sessionUser");
    	if(sessionUser==null){
    		data.put("flag", false);
    		return data;
    	}
    	Map map=(Map)sessionUser;
    	List<Map> row=recordService.getXminfoByKey(map.get("deptid").toString(),Integer.parseInt(map.get("typeid").toString()));
    	if(row==null || row.isEmpty()){
    		data.put("flag", false);
    	}else{
    		data.put("flag", true);
    	}
        return data;
    }
    
    
}
