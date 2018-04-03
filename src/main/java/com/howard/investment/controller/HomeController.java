package com.howard.investment.controller;

import com.howard.investment.service.impl.UserServiceImpl;
import com.howard.investment.tools.Tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * Created by WangHua on 18/3/28.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserServiceImpl userService;

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
    
    @RequestMapping("login.html")
    public String loginView(HttpSession session){
        return "login";
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
    	data.put("flag", true);
        return data;
    }
}
