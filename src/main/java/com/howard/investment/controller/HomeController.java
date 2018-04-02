package com.howard.investment.controller;

import com.howard.investment.service.impl.UserServiceImpl;
import com.howard.investment.tools.Tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String index(HttpSession session){
    	Object sessionUser=session.getAttribute("sessionUser");
    	if(sessionUser==null){
    		return "login";
    	}
        return "index";
    }
}
