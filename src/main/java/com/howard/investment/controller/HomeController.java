package com.howard.investment.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.howard.investment.tools.HttpUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.howard.investment.service.impl.ProjectInfoServiceImpl;
import com.howard.investment.service.impl.RecordServiceImpl;
import com.howard.investment.service.impl.UserServiceImpl;
import com.howard.investment.tools.Tools;
import org.springframework.web.servlet.ModelAndView;

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
	@Autowired
	private ProjectInfoServiceImpl projectinfoserviceimpl;

	@ResponseBody
	@RequestMapping("login")
	public Map<String, Object> login(@RequestParam String userName, @RequestParam String password,
			HttpSession session) {
		Map<String, Object> data = new HashMap<>();
		password = Tools.encrypt16(password);
		Map map = userService.getUserByKey(userName, password);
		if (map != null) {
			session.setAttribute("sessionUser", map);
			data.put("flag", true);
		} else {
			data.put("flag", false);
			data.put("msg", "帐号密码错误");
		}
		return data;
	}

	@ResponseBody
	@RequestMapping("cancel")
	public Map<String, Object> cancel(HttpSession session) {
		Map<String, Object> data = new HashMap<>();
		Object sessionUser = session.getAttribute("sessionUser");
		if (sessionUser != null) {
			session.setAttribute("sessionUser", null);
			data.put("flag", true);
		} else {
			data.put("msg", "登录超时！");
			data.put("flag", false);
		}
		return data;
	}

	@ResponseBody
	@RequestMapping("matchDept")
	public Map<String, Object> matchDept() {
		Map<String, Object> data = new HashMap<>();
		List<Map> list = recordService.getXmfrdwAll();
		int row = 0;
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				int o = recordService.updateByKey(list.get(i).get("bmdm").toString(),
						list.get(i).get("dwmc").toString());
				if (o > 0) {
					row += o;
				}
			}
		}
		data.put("row", row);
		data.put("flag", true);
		return data;
	}

	@ResponseBody
	@RequestMapping("deleteXmById")
	public Map<String, Object> deleteXmById(@RequestParam String id) {
		Map<String, Object> data = new HashMap<>();
		int row = recordService.deleteXmById(id);
		if (row != 0) {
			data.put("flag", true);
			return data;
		} else {
			data.put("msg", "删除失败!");
			data.put("flag", false);
			return data;
		}
	}

	@RequestMapping("login.html")
	public String loginView(HttpSession session) {
		return "login";
	}

	@RequestMapping("record.html")
	public String recordView(@RequestParam(required = false) Integer pageNum,
			@RequestParam(required = false) String type,@RequestParam(required = false) String bz, Model model,HttpSession session) {
		if (pageNum == null || pageNum < 1) {
			model.addAttribute("pageNum", 1);
			pageNum = 15;
		} else {
			model.addAttribute("pageNum", pageNum);
			pageNum *= 15;
		}
		Object sessionUser = session.getAttribute("sessionUser");
		if (sessionUser == null) {
			return "login";
		}
		Map map=(Map)sessionUser;
		Map dept=recordService.getDeptById(map.get("deptid").toString());
		String code=null;
		if(dept!=null && dept.containsKey("code")){
			code=dept.get("code").toString();
		}
		List<Map> list = recordService.getRecordByKey(pageNum, bz,type,map.get("typeid").toString(),code);
		int totalCount = recordService.getRecordByKeyCount(bz,type,map.get("typeid").toString(),code);
		for (int i = 0, j = list.size(); i < j; i++) {
			if (!list.get(i).containsKey("bmdm")) {
				list.get(i).put("bmdm", "");
			}
			if (!list.get(i).containsKey("jhztz")) {
				list.get(i).put("jhztz", "");
			}
			if (!list.get(i).containsKey("bz")) {
				list.get(i).put("bz", "");
			}
		}
		model.addAttribute("list", list);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageCount", totalCount % 15 == 0 ? totalCount / 15 : totalCount / 15 + 1);
		model.addAttribute("pageSize", "15");
		model.addAttribute("key", "?type=" + type + "&pageNum=");
		return "record";
	}

	@RequestMapping("dwxx.html")
	public String dwxxView(@RequestParam(required = false) Integer pageNum, @RequestParam(required = false) String bmdm,
			@RequestParam(required = false) String dwmc, Model model) {
		if (pageNum == null || pageNum < 1) {
			model.addAttribute("pageNum", 1);
			pageNum = 15;
		} else {
			model.addAttribute("pageNum", pageNum);
			pageNum *= 15;
		}
		if (dwmc != null && !"".equals(dwmc) && !"null".equals(dwmc)) {
			dwmc = "'%%" + dwmc + "%%'";
		}
		List<Map> list = recordService.getXmdwxxByKey(pageNum, bmdm, dwmc);
		int totalCount = recordService.getXmdwxxByKeyCount(bmdm, dwmc);
		model.addAttribute("list", list);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageCount", totalCount % 15 == 0 ? totalCount / 15 : totalCount / 15 + 1);
		model.addAttribute("pageSize", "15");
		model.addAttribute("key", "?bmdm=" + bmdm + "&dwmc=" + dwmc + "&pageNum=");
		return "dwxx";
	}

	@RequestMapping("index.html")
	public String index(HttpSession session, Model model) {
		Object sessionUser = session.getAttribute("sessionUser");
		if (sessionUser == null) {
			return "login";
		}
		Map map = (Map) sessionUser;
		model.addAttribute("userName", map.get("username").toString());
		if ("10".equals(map.get("typeid").toString())) {
			model.addAttribute("userLevel", "局用户");
		} else if ("2".equals(map.get("typeid").toString())) {
			model.addAttribute("userLevel", "部门用户");
		} else if ("0".equals(map.get("typeid").toString())) {
			model.addAttribute("userLevel", "超级管理员");
		}
		return "index";
	}

	@ResponseBody
	@RequestMapping("userinfo")
	public Map<String, Object> userinfo(HttpSession session) {
		Map<String, Object> data = new HashMap<>();
		Object sessionUser = session.getAttribute("sessionUser");
		if (sessionUser == null) {
			data.put("flag", false);
			return data;
		}
		Map map = (Map) sessionUser;
		data.put("userName", map.get("username").toString());
		if ("10".equals(map.get("typeid").toString())) {
			data.put("userLevel", "局用户");
		} else if ("2".equals(map.get("typeid").toString())) {
			data.put("userLevel", "部门用户");
		} else if ("0".equals(map.get("typeid").toString())) {
			data.put("userLevel", "超级管理员");
		}
		data.put("typeId", map.get("typeid").toString());
		data.put("flag", true);
		return data;
	}

	@ResponseBody
	@RequestMapping("editXmByKey")
	public Map<String, Object> editXmByKey(@RequestParam Map<String, String> map) {
		Map<String, Object> data = new HashMap<>();
		int row = recordService.updateXmByKey(map);
		if (row > 0) {
			data.put("flag", true);
		} else {
			data.put("msg", "保存失败");
			data.put("flag", false);
		}
		return data;
	}

	@ResponseBody
	@RequestMapping("getDept")
	public Map<String, Object> getDept() {
		Map<String, Object> data = new HashMap<>();
		List<Map> list = recordService.getDept();
		data.put("list", list);
		return data;
	}

	@RequestMapping(value = "deptxmbaInfo", method = RequestMethod.GET)
	public String deptInfo(HttpServletRequest request, @RequestParam("id") String id, Model model,
			@RequestParam("type") String type) throws Exception {
		List<Map> queryProjectInfo = recordService.getXmByIds(id);
		queryProjectInfo.get(0).put("type", type);
		String xxid = request.getParameter("xxid");
		queryProjectInfo.get(0).put("xxid", xxid);
		if(queryProjectInfo.get(0).get("jhztz")==null || "".equals(queryProjectInfo.get(0).get("jhztz").toString())){
			queryProjectInfo.get(0).put("jhztz","");
		}
		model.addAttribute("info", queryProjectInfo.get(0));

		// 根据taype走不同页面

		return "recordInfoVerify";
	}

	@RequestMapping(value = "recordInfoVerify",method = RequestMethod.POST)
	public String recordInfoVerify(HttpServletRequest request,@RequestParam Map map) {
		recordService.updateInfo(map.get("xxid").toString(),map.get("selectRadio").toString());
		return  "redirect:infoView";
	}

	@ResponseBody
	@RequestMapping("getXmbaxxByDeptBmdm")
	public Map<String, Object> getXmbaxxByDeptId(@RequestParam String bmdm) {
		Map<String, Object> data = new HashMap<>();
		List<Map> list = recordService.getXmbaxxByDeptBmdm(bmdm);
		if (list != null && list.size() > 0) {
			data.put("list", list);
			data.put("flag", true);
		} else {
			data.put("flag", false);
			data.put("msg", "未查到该部门下项目");
		}
		return data;
	}

	@ResponseBody
	@RequestMapping(value = "sendMessageBa")
	public Map<String, Object> sendMessageBa(HttpServletRequest request, @RequestParam("bmdm") String bmdm,
			@RequestParam("title") String title, @RequestParam("xms") String xms) throws Exception {
		HttpSession session = request.getSession();
		Map maps = (Map) session.getAttribute("sessionUser");
		if(xms.indexOf(",")!=-1){
			xms=xms.substring(0, xms.length() - 1);
		}
		List<Map> listXm = recordService.getXmByIds(xms);
		if("请选择".equals(bmdm)){
			bmdm="0";
		}
		String fsz = "统计局";
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Map<String, Object> map = new HashMap<String, Object>();
		String first=null;
		map.put("fsz", "统计局");
		if (maps.get("typeid").toString().equals("2")) {
			first="审批消息";
			map.put("xminfotype", 3);
		} else if (maps.get("typeid").toString().equals("10")) {
			first="新备案消息";
			map.put("xminfotype", 11);
		}
		int row = 0;
		for (int i = 0; i < listXm.size(); i++) {
		    if(listXm.get(i).get("bz")!=null){
		        if(listXm.get(i).get("bz").toString().equals("0")){
                    continue;
                }
            }
			map.put("bmdm", Integer.parseInt(bmdm));
			map.put("xmmc", listXm.get(i).get("xmmc").toString());
			map.put("xmdwmc", listXm.get(i).get("xmfrdw").toString());
			map.put("title", title);
			map.put("inputdate", df.format(day));
			map.put("xmid", Integer.parseInt(listXm.get(i).get("id").toString()));
			row += projectinfoserviceimpl.sendMessage(map);
			List<Map> users = null;
			if (maps.get("typeid").toString().equals("10")) {
				users = userService.getUserByBmdm(bmdm);

			}else{
				users = userService.getUserByJu();
			}
			for (int j = 0; j < users.size(); j++) {
				Tools.sendWxMsg(users.get(j).get("openid").toString(),first,map.get("xmmc").toString(),title);
			}
		}
		if (row != 0) {
			map.put("flag", true);
		} else {
			map.put("msg", "发送失败");
			map.put("flag", false);
		}

		return map;
	}

	@ResponseBody
	@RequestMapping("bazxm")
	public Map<String, Object> bazxm() {
		Map<String, Object> data = new HashMap<>();
		try {
			List<Map> list = recordService.getXmbaxxByJhztz();
			int rowXmxx = 0;
			for (int i = 0; i < list.size(); i++) {
				if(!list.get(i).containsKey("bmdm")){
					list.get(i).put("bmdm",0);
				}
				rowXmxx += recordService.insertXmxx(list.get(i));
			}
			int rowXmbaxx = recordService.updateXmByJhztz();
			data.put("rowXmbaxx", rowXmbaxx);
			data.put("rowXmxx", rowXmxx);
			data.put("flag", true);
		} catch (Exception e) {
			e.printStackTrace();
			data.put("flag", false);
			data.put("msg", "操作失败");
		}
		return data;
	}
	
	@RequestMapping("updateInfo")
	public String updateInfo(@RequestParam String xxid) {
		recordService.updateInfo(xxid,"1");
		return "redirect:infoView";
	}
	@RequestMapping(value = "infoView", method = RequestMethod.GET)
	public String infoView(HttpServletRequest request, Model model) throws Exception {

		HttpSession session = request.getSession();
		Map<String, Object> map = (Map<String, Object>) session.getAttribute("sessionUser");
		if (map.get("typeid").toString().equals("10")) {// 据用户
			model.addAttribute("msgtype", 3);

		} else if (map.get("typeid").toString().equals("2")) {// 部门用户
			model.addAttribute("msgtype", 11);
		}

		return "info";
	}

	@ResponseBody
	@RequestMapping(value = "info", method = RequestMethod.POST)
	public List<Map> info(HttpServletRequest request) throws Exception {

		// 查询统计局发送给部门的消息//部门用户只能看到属于自己本门的
		// 查询客户给局用户发送的消息//这个不需要划分，只要是局用户可以查询所有消息
		HttpSession session = request.getSession();
		List<Map> list = null;
		Map<String, Object> map = (Map<String, Object>) session.getAttribute("sessionUser");
		if (map.get("typeid").toString().equals("10")) {
			// 据用户
			map.put("msgtype", 3);
			list = projectinfoserviceimpl.juuser(map);
		} else if (map.get("typeid").toString().equals("2")) {
			// 获取deptid 部门用户
			// 部门用户需要查询xmdm,根据用户的 deptid
			Map mapxmdm = projectinfoserviceimpl.getbmdm(Integer.parseInt(map.get("deptid").toString()));
			map.put("msgtype", 11);
			map.put("bmdm", mapxmdm.get("code"));
			list = projectinfoserviceimpl.juuser(map);
		}
		return list;
	}

	@ResponseBody
	@RequestMapping("getxminfo")
	public Map<String, Object> getxminfo(HttpSession session) {
		Map<String, Object> data = new HashMap<>();
		Object sessionUser = session.getAttribute("sessionUser");
		if (sessionUser == null) {
			data.put("flag", false);
			return data;
		}
		Map map = (Map) sessionUser;
		List<Map> row2 = recordService.getXminfoByKey(map.get("deptid").toString(), 2);
		List<Map> row3 = recordService.getXminfoByKey(map.get("deptid").toString(), 3);
		List<Map> row10 = recordService.getXminfoByKey(map.get("deptid").toString(), 10);
		List<Map> row11 = recordService.getXminfoByKey(map.get("deptid").toString(), 11);
		data.put("row2", !row2.isEmpty());
		data.put("row3", !row3.isEmpty());
		data.put("row10", !row10.isEmpty());
		data.put("row11", !row11.isEmpty());
		data.put("flag", true);
		return data;
	}

	@RequestMapping("getOpenId.html")
	public String getOpenId(HttpServletRequest request,HttpSession session) {
		String code = request.getParameter("code");
		if (code == null || code.equals("")) {
			//调用微信网页授权
			String uri = request.getScheme() + "://" + request.getServerName() + request.getRequestURI();
			String redirectUri = "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
					"wx67a2f39a7fec8548" +
					"&redirect_uri=" +
					uri +
					"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
			return redirectUri;
		} else {
			//通过code获取openid
			String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" + "?appid=" + "wx67a2f39a7fec8548" +
					"&secret=" + "91fe35d3a2f94049370ee981bf29595a" +
					"&code=" + code +
					"&grant_type=authorization_code";
			String result = HttpUtils.getJson(accessTokenUrl);
			JSONObject json = JSONObject.fromObject(result);
			if (json.containsKey("openid")) {
			    session.setAttribute("openId",json.getString("openid"));
				System.out.println(json.getString("openid"));
			}
		}
		return "loginOpenId";
	}
    @RequestMapping("loginOpenId")
    public String loginOpenId(@RequestParam String userName, @RequestParam String password,Model model, HttpSession session) {
        password = Tools.encrypt16(password);
        Map map = userService.getUserByKey(userName, password);
        if (map != null) {
            Object openId = session.getAttribute("openId");
            if(openId!=null && !"".equals(openId.toString())){
                map.put("openId",openId.toString());
                userService.updateUserByKey(map);
            }
            session.setAttribute("sessionUser", map);
        } else {
            model.addAttribute("msg","密码错误");
            return "fail";
        }
        return "success";
    }


}
