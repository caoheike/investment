package com.howard.investment.tools;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * @Description:MD5加解密工具 
 * @author:liuyc 
 * @time:2016年5月23日 上午11:11:16 
 */  
public class Tools {  
    /** 
     * @Description:加密-32位小写 
     * @author:liuyc 
     * @time:2016年5月23日 上午11:15:33 
     */  
    public static String encrypt32(String encryptStr) {  
        MessageDigest md5;  
        try {  
            md5 = MessageDigest.getInstance("MD5");  
            byte[] md5Bytes = md5.digest(encryptStr.getBytes());  
            StringBuffer hexValue = new StringBuffer();  
            for (int i = 0; i < md5Bytes.length; i++) {  
                int val = ((int) md5Bytes[i]) & 0xff;  
                if (val < 16)  
                    hexValue.append("0");  
                hexValue.append(Integer.toHexString(val));  
            }  
            encryptStr = hexValue.toString();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
        return encryptStr;  
    }

    public static String sendWxMsg(String openId,String title,String name,String msg) throws IOException {
        System.out.println(openId+"----"+name+"------"+msg);
        String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+"wx67a2f39a7fec8548"+"&secret="+ "91fe35d3a2f94049370ee981bf29595a";
        String result = HttpUtils.getJson(accessTokenUrl);
        System.out.println(result);
        JSONObject json = JSONObject.fromObject(result);
        JSONObject data = new JSONObject();
        JSONObject message = new JSONObject();
        data.put("touser",openId);
        data.put("template_id","bv_s6GY6lR84OYswIPiZIIKh2SmPk_LsywF4XU_V814");
        data.put("topcolor","#FF0000");
        JSONObject first = new JSONObject();
        first.put("value",title);
        first.put("color","#e81f32");
        message.put("first",first);
        JSONObject keyword1 = new JSONObject();
        keyword1.put("value",name);
        keyword1.put("color","#e81f32");
        message.put("keyword1",keyword1);
        JSONObject keyword2 = new JSONObject();
        keyword2.put("value","发送成功");
        keyword2.put("color","#e81f32");
        message.put("keyword2",keyword2);
        JSONObject keyword3 = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        keyword3.put("value",sdf.format(new Date()));
        keyword3.put("color","#e81f32");
        message.put("keyword3",keyword3);
        JSONObject remark = new JSONObject();
        remark.put("value",msg);
        remark.put("color","#173177");
        message.put("remark",remark);
        data.put("data",message);
        if(json.containsKey("access_token")){
            result = HttpUtils.sendInfo("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+json.getString("access_token"),data.toString());
            System.out.println(result);
        }
        return result;
    }
  
    /** 
     * @Description:加密-16位小写 
     * @author:liuyc 
     * @time:2016年5月23日 上午11:15:33 
     */  
    public static String encrypt16(String encryptStr) {  
        return encrypt32(encryptStr).substring(8, 24);  
    }

} 