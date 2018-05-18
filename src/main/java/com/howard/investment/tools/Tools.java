package com.howard.investment.tools;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;

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

    public static String sendWxMsg(String openId,String name,String title) throws IOException {
        System.out.println(openId+"----"+name+"------"+title);
        String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+"wx9fc89e5873902f0c"+"&secret="+ "d887bf689e61f5e9b29f27c31bd659ed";
        String result = HttpUtils.getJson(accessTokenUrl);
        System.out.println(result);
        JSONObject json = JSONObject.fromObject(result);
        JSONObject data = new JSONObject();
        data.put("touser",openId);
        data.put("template_id","hPFO7lsd8rAnuQLBQG3WeyQCNAyq-HAYxWOKeuthJEQ");
        data.put("topcolor","#FF0000");
        JSONObject msg = new JSONObject();
        JSONObject xmmc = new JSONObject();
        xmmc.put("value",name);
        xmmc.put("color","#e81f32");
        msg.put("xmmc",xmmc);
        JSONObject info = new JSONObject();
        info.put("value",title);
        info.put("color","#173177");
        msg.put("info",info);
        data.put("data",msg);
        result = HttpUtils.sendInfo("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+json.getString("access_token"),data.toString());
        System.out.println(result);
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
  
//    public static void main(String[] args) {  
//        String encryptStr = "22222222222,./.,./.,./!@#$%^&*()";  
//        System.out.println(Tools.encrypt32(encryptStr));  
//        System.out.println(Tools.encrypt16(encryptStr));  
//    }  
} 