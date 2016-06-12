/**   
* @Title: TestController.java 
* @Package com.frame.kangan.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author kang.an@ele.me   
* @date 2016年5月26日 下午6:12:19 
* @version V1.0   
*/
package com.frame.kangan.service.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

@Service
public class QiniuServiceImpl {
	
    private static final String ACCESS_KEY = "Yzty2rHQH7ggINmFg40lhgsYs9g0u8oP-w8ovWp-";  
    private static final String SECRET_KEY = "rvgjmyGaDsswsazZDEZwlDfXA4CoZ8R9w2ZkAIQY";  
    /**默认上传空间*/  
    private static final String BUCKET_NAME = "frame";  
    /**空间默认域名*/  
    private static final String BUCKET_HOST_NAME = "http://o7s4lsyon.bkt.clouddn.com";  
      
    private static UploadManager uploadManager = new UploadManager();  
      
    private static int LIMIT_SIZE = 1000;  
	

    public static String uploadFile(InputStream inputStream,String name) throws IOException {    
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);  
        String token = auth.uploadToken(BUCKET_NAME);  
        byte[] byteData = IOUtils.toByteArray(inputStream);  
        Response response = uploadManager.put(byteData, name, token, null, "image/jpeg", false);  
        inputStream.close();  
        return response.bodyString();  
    }  
	
    public static String getFileAccessUrl(String key) throws QiniuException {  
        return BUCKET_HOST_NAME + "/" + key;  
    }  
	


}