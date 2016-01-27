package com.cat.manage.common.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cat.manage.common.model.Srm;
import com.google.common.collect.Lists;

/**
 * @Description: 
 * @author 王航
 * @date 2015年12月11日 下午4:17:02
 */
@RestController
@RequestMapping("/upload")
public class AController {
	
	@RequestMapping("/read")
	public Srm uploadAndRead(@RequestParam("file") MultipartFile uploadFile, HttpServletRequest request) throws Exception{
		List<String> title = Lists.newArrayList();
		title.add("org");
		title.add("userId");
		title.add("type");
		List<?> result = UploadUtil.uploadAndReadForExcel(uploadFile, request, title, null);
		return new Srm().setResultCode("0").setResultMessage("上传读取成功").addAll(result);
	}
	
}
