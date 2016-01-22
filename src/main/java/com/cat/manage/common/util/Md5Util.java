package com.cat.manage.common.util;

import java.security.MessageDigest;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: MD5 工具类
 * @author 
 * @date 
 */
public class Md5Util {
	
	private Md5Util() {}

	/**
	 * 获得字符串摘要值 
	 */
	public static String digest(String value) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] byteArr = md5.digest(value.getBytes(Constant.DEFAULT_CHARSET));
            return toHexString(byteArr);
            
        } catch (Exception e) {
            LOG.error("生成MD5值出错", e);
            return "";
        }
    }

	/**
	 * 获得字符数组摘要值
	 */
    public static String digest(byte[] bytes) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] byteArr = md5.digest(bytes);
            return toHexString(byteArr);
            
        } catch (Exception e) {
            LOG.error("生成MD5值出错", e);
            return "";
        }
    }
    
    /**
     * 获得随机数
     */
    public static String getRandomSalt() {
    	Random rnd = new Random();
    	return String.valueOf(rnd.nextInt(10000));
    }
 
    /**
     * 将字节数组转化为十六进制字符串 
     */
    private static String toHexString(byte byteArr[]) {
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0, len = byteArr.length; i < len; i++)
        	sb.append(byte2HexStr(byteArr[i]));
 
        return sb.toString();
    }
    
    /**
     * 将字节转化为十六进制字符串
     */
    private static String byte2HexStr(byte abyte) {
    	StringBuilder sb = new StringBuilder(2);
    	
    	sb.append(Constant.HEX_TABLE.charAt((abyte & 0xF0) >> 4));
    	sb.append(Constant.HEX_TABLE.charAt(abyte & 0x0F));
    	
    	return sb.toString();
    }

	private static final Logger LOG = LoggerFactory.getLogger(Md5Util.class);
}
