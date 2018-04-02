package com.howard.investment.controller;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.howard.investment.service.impl.ProjectInfoServiceImpl;

/**
 * 文件上传的Controller
 * 
 * @author 单红宇(CSDN CATOOP)
 * @create 2017年3月11日
 */
@Controller
public class FileUploadController {
	
    @Autowired
    private ProjectInfoServiceImpl projectinfoserviceimpl;

    // 访问路径为：http://ip:port/upload
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String upload() {
        return "/fileupload";
    }

    // 访问路径为：http://ip:port/upload/batch
    @RequestMapping(value = "/upload/batch", method = RequestMethod.GET)
    public String batchUpload() {
        return "mutifileupload";
    }

    /**
     * 文件上传具体实现方法（单文件上传）
     *
     * @param file
     * @return
     * 
     * @author 单红宇(CSDN CATOOP)
     * @create 2017年3月11日
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                // 这里只是简单例子，文件直接输出到项目路径下。
                // 实际项目中，文件需要输出到指定位置，需要在增加代码处理。
                // 还有关于文件格式限制、文件大小限制，详见：中配置。
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(file.getOriginalFilename())));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            }
            return "上传成功";
        } else {
            return "上传失败，因为文件是空的.";
        }
    }

    /**
     * 多文件上传 主要是使用了MultipartHttpServletRequest和MultipartFile
     *
     * @param request
     * @return
     * 
     * @author 单红宇(CSDN CATOOP)
     * @create 2017年3月11日
     */
    @RequestMapping(value = "/upload/batch", method = RequestMethod.POST)
    public @ResponseBody String batchUpload(HttpServletRequest request,@RequestParam("id") String id,@RequestParam("msgtype") String msgtype) {
    	 String basePath=request.getServletContext().getRealPath("/upload");

         //拼接成完整的指定的文件路径名，创建新文件
         String filePath = basePath+File.separator;
         File filess = new File(filePath);
         if(!filess.exists()){
        	 filess.mkdir();   
         }
        Map<String, Object> map=new HashMap<String,Object>();
        String msg="";
    	if(msgtype.equals("0")){
    		//用户具有上传权限
    		 List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
    	        MultipartFile file = null;
    	        BufferedOutputStream stream = null;
//    	        String filepath="/Users/hongzheng/Downloads/file";
    	        String file1="";
    	        String file2="";
    	        String file3="";
    	        String file4="";
    	        String file5="";
    	        String path="";
    	        for (int i = 0; i < files.size(); ++i) {
    	            file = files.get(i);
    	            if (!file.isEmpty()) {
    	                try {
    	                    byte[] bytes = file.getBytes();
    	                    path=filePath+"/"+ file.getOriginalFilename();
    	                    String imgname= file.getOriginalFilename();
    	                    stream = new BufferedOutputStream(new FileOutputStream(new File(path)));
    	                    stream.write(bytes);
    	                    stream.close();
    	                    
    	                    if(i==1){
    	                    	file1="upload/"+imgname;
    	                    }
    	                    if(i==2){
    	                    	file2="upload/"+imgname;;
    	                    }
    	                    if(i==3){
    	                    	file3="upload/"+imgname;;
    	                    }
    	                    if(i==4){
    	                    	file4="upload/"+imgname;;
    	                    }
    	                    if(i==5){
    	                    	file5="upload/"+imgname;;
    	                    }
    	                   
    	                    	
    	                   
    	                } catch (Exception e) {
    	                    stream = null;
    	                    return "You failed to upload " + i + " => " + e.getMessage();
    	                }
    	            } else {
    	                return "You failed to upload " + i + " because the file was empty.";
    	            }
    	        }
    	        
    	        /**
    		     * 修改file路径 根据file路径 判断用户是否上传
    		     */
    	
    	        map.put("id", id);
    	        map.put("file1",file1);
    	        map.put("file2",file2);
    	        map.put("file3",file3);
    	        map.put("file4",file4);
    	        map.put("file5",file5);
    	        projectinfoserviceimpl.updateInfo(map);
    	        msg="保存成功";
    	}else{
    		//具有审批权限
       		String radio = request.getParameter("selectRadio");
       		String xxid=request.getParameter("xxid");
    			System.out.println(radio);
    			map.put("status",Integer.parseInt(radio));
    			map.put("id",id);
    		    projectinfoserviceimpl.updateInfos(map); //修改审批
    		    map.put("xxid",xxid);
    		    projectinfoserviceimpl.updatexiaoxi(map);//修改处理状态
    		    msg="审批成功";
    	}
       
        

        
        return msg;
    }
}