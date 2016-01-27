package com.cat.manage.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Closer;

/**
 * @Description: 上传工具类
 * @author
 * @date 2016年1月27日 上午9:45:28
 */
public class UploadUtil {
    private UploadUtil() {}
    
    private static final String UPLOAD_DIRECTORY = "upload";
    
    /**
     * 上传文件至webroot目录下的upload文件夹, 上传失败返回null
     */
    public static File saveFileToUploadFolder(MultipartFile uploadFile, HttpServletRequest request) {
        File serverSaveFile = null;
        
        if (uploadFile == null) 
            return serverSaveFile;

        String webroot = request.getServletContext().getRealPath("/");
        String upload  = webroot + File.separatorChar + UPLOAD_DIRECTORY;

        String date = new DateTime().toString("yyyyMMdd");
        String name    = uploadFile.getOriginalFilename();
        
        Closer closer = Closer.create();
        try {
            BufferedInputStream bufferedStream = new BufferedInputStream(uploadFile.getInputStream());
            closer.register(bufferedStream);
            
            String serverSaveFilePath = upload + File.separatorChar + date;
            File uploadDir = new File(serverSaveFilePath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            serverSaveFile = new File(serverSaveFilePath + File.separatorChar + name);
            if (!serverSaveFile.exists()) {
                serverSaveFile.createNewFile();
            }
            
            OutputStream output = new FileOutputStream(serverSaveFile);
            closer.register(output);
            
            int len = -1;
            byte[] buffer =  new byte[5 * 1024];
            while ((len = bufferedStream.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
            output.flush();
            
        } catch (Exception e) {
            LOG.info("", e);
            serverSaveFile = null;
            
        } finally {
            closeResource(closer);
        }
        
        return serverSaveFile;
    }
    
    /**
     * 上传并读取Excel, 默认第一行，第一工作簿开始读取
     * @param uploadFile
     * @param request
     * @return
     * @throws Exception 
     */
    public static List<?> uploadAndReadForExcel(MultipartFile uploadFile, HttpServletRequest request, List<String> title, Class<?> arg) throws Exception{
    	File file = saveFileToUploadFolder(uploadFile, request);
    	if(file == null)
    		return null;
    	return ExcelUtil.importExcel(file, title, arg);
    }
    
    /**
     * 上传并读取Excel,第一工作簿开始读取
     * @param uploadFile
     * @param request
     * @return
     * @throws Exception 
     */
    public static List<?> uploadAndReadForExcel(MultipartFile uploadFile, HttpServletRequest request, List<String> title, Class<?> arg, int rowNum) throws Exception{
    	File file = saveFileToUploadFolder(uploadFile, request);
    	if(file == null)
    		return null;
    	return ExcelUtil.importExcel(file, title, arg, 0, rowNum);
    }
    
    /**
     * 上传并读取Excel
     * @param uploadFile
     * @param request
     * @return
     * @throws Exception 
     */
    public static List<?> uploadAndReadForExcel(MultipartFile uploadFile, HttpServletRequest request, List<String> title, Class<?> arg, int sheetNum, int rowNum) throws Exception{
    	File file = saveFileToUploadFolder(uploadFile, request);
    	if(file == null)
    		return null;
    	return ExcelUtil.importExcel(file, title, arg, sheetNum, rowNum);
    }
    
    /**
     * 关闭资源
     */
    private static void closeResource(Closer closer) {
        if (closer == null)
            return;
        
        try {
            closer.close();
        } catch (Exception e) {
            LOG.info("", e);
        }
    }
    
    private static final transient Logger LOG = LoggerFactory.getLogger(UploadUtil.class);
}
