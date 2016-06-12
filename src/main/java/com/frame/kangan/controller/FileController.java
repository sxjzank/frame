/**   
* @Title: FileController.java 
* @Package com.frame.kangan.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author kang.an@ele.me   
* @date 2016年5月26日 上午11:30:56 
* @version V1.0   
*/
package com.frame.kangan.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.frame.kangan.data.mapper.FrameImageMapper;
import com.frame.kangan.data.po.FrameImage;
import com.frame.kangan.service.impl.QiniuServiceImpl;

/** 
* @ClassName: FileController 

* @Description: TODO(这里用一句话描述这个类的作用) 

* @author kang.an@ele.me

* @date 2016年5月26日 上午11:30:56 
*  
*/
@RestController
public class FileController {
   
	@Autowired
	private QiniuServiceImpl qiniuServiceImpl;
	
	@Autowired
	private FrameImageMapper frameImageMapper;
	
    /**
     * 文件上传具体实现方法;
     * @param file
     * @return
     * @throws IOException 
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file")MultipartFile file) throws IOException{
       if(!file.isEmpty()){
           try {
        	   	String hashCode = qiniuServiceImpl.uploadFile(file.getInputStream(),file.getOriginalFilename());
        	   	FrameImage frameImage = new FrameImage();
        	   	frameImage.setHashcode(hashCode);
        	   	frameImage.setType(1);
        	   	frameImage.setFileName(file.getOriginalFilename());
        	   	frameImageMapper.insertSelective(frameImage);
        	   	
        	   	return hashCode;
           } catch (FileNotFoundException e) {
              e.printStackTrace();
              return"上传失败,"+e.getMessage();
           } catch (IOException e) {
              e.printStackTrace();
              return"上传失败,"+e.getMessage();
           }
       }else{
   
    	 
           return"上传失败，因为文件是空的.";
       }
    }
}
