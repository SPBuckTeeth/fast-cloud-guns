package com.fast.cloud.core.util;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.binary.Base64;

/**
 * 所有关于字符串的出来以及日期的格式化
 * @author Eric
 *
 */
public class CommonUtils {
    public static String HZ_QUOTATION_MSG = "尊敬的客户，您的询价（%s--%s）已有报价，请及时查看！";
//    public static String ADD_WORK_ORDER_URL = "http://localhost:8082/house-cloud-app-manage/cargo/addCargoWorkOrder";
    public static String ADD_WORK_ORDER_URL = "http://warehouse.mrcargo.com/house-cloud-app-manage/cargo/addCargoWorkOrder";
    public static String PROJECT_REAL_PATH = "";
    public static String GET_YUN_CANG_USER_URL = "http://warehouse.mrcargo.com/house-cloud-app-manage/user/getSysUser";
    public static String SAVE_YUN_CANG_USER_URL = "http://warehouse.mrcargo.com/house-cloud-app-manage/user/userAssociate";
	
	public final static SimpleDateFormat DATEFORMAT_YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public final static SimpleDateFormat DATEFORMAT_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	public final static SimpleDateFormat DATEFORMAT_YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddhhmmss");
	
	public static String HOST = "";
	public static int PORT = 0;
	public static String FROM = "";
	public static String SEND_NAME = "";
	public static String USER_NAME = "";
	public static String PASS_WORD = "";
	public static String MAIL_CC = "";//抄送
	
	
	static{
		Properties properties = new Properties();
        InputStream input = null;
        try {
            input = MailUtils.class.getResourceAsStream("/mail.properties");
            try {
				properties.load(input);
				HOST = properties.getProperty("mail.smtp.host");
				PORT = Integer.parseInt(properties.getProperty("mail.smtp.port"));
				FROM = properties.getProperty("mail.smtp.sender");
				SEND_NAME = properties.getProperty("mail.smtp.senderName");
				SEND_NAME = "卡果先生智能空运系统";
				USER_NAME = properties.getProperty("mail.smtp.sender");
				PASS_WORD = properties.getProperty("password");
				
				MAIL_CC = properties.getProperty("mail.cc");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } finally{
            if (input!=null) {
                try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
	}
	
	/**
	 * 权限SQL
	 */
//	select em.id  from es_member em where em.enterprise_id in(
//			select DISTINCT u.enterprise_id as enterids from sys_user u 
//			left join sys_department sd on u.department_id in (sd.id)
//			where  FIND_IN_SET(sd.id, sysDepartmentChildNode('1'))
//		)
//    <if test="authUserId != null">
//		and member_id in (
//			select id from es_member where enterprise_id in(
//				select distinct u.enterprise_id as enterids from sys_user u 
//				left join sys_department sd on u.department_id in (sd.id)
//				where  FIND_IN_SET(sd.id, sysDepartmentChildNode(#{authUserId}))
//			)
//		)
//	</if>
	
	
	
	/**
	 * 获取RandomID(UUID)
	 * @return
	 */
	public static String randomID(){
		return UUID.randomUUID().toString().replace("-","");
	}

	/**
	 * 获取随机编号(No)
	 * @return
	 */
	public static String serialNo(){
		return DATEFORMAT_YYYYMMDDHHMMSS.format(new Date());
	}
	
	/**
	 * 获取当前时间的String字符串
	 * @return
	 */
	public static String currentTimeStr(){
		return DATEFORMAT_YYYY_MM_DD_HH_MM_SS.format(new Date());
	}
	
	/**
	 * 获取随机数
	 * @return
	 */
	public static String getRandomNum(int x) {
		Random random = new Random();
		String sRand = "";
		for (int i = 0; i < x; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}
		return sRand;
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static Date currentTime(){
		return new Date();
	}
	
	public static String currentDateStr(){
		return DATEFORMAT_YYYY_MM_DD.format(new Date());
	}
	
	  // 加密  
    public static String getBase64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = new Base64().encodeToString(str.getBytes());  
        }  
        return s;  
    }  
  
    // 解密  
    public static String getFromBase64(String s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {  
        	Base64 decoder = new Base64();  
            try {  
                b = decoder.decode(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    } 
    
    public static boolean validateEmail(String email){
    	Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
    }

	public static Map<String, Object> setPageParam(HttpServletRequest request) {
		Map<String, Object> paramMap = new HashMap<>();
		Map<String, String[]> parameterMap = request.getParameterMap();
		for (String key : parameterMap.keySet()) {
			if (key.equals("pageIndex") || key.equals("pageSize")) {
				String[] values = parameterMap.get(key);
				paramMap.put(key,Integer.valueOf(values[0]));
			}
		}
		return paramMap;
	}
    
	public static void main(String[] args) {
//		System.out.println(randomID());
//		System.out.println(serialNo());
//		System.out.println(currentTimeStr());
//		System.out.println(currentTime());
//		System.out.println(currentDateStr());
//		System.out.println("2ece62e6e09c4c21b74bae46bf4c1ca0".length());
//		System.out.println(getBase64("fcb5ddbfe9d54aefad0ac539736a4842"));
//		System.out.println(getFromBase64("fcb5ddbfe9d54aefad0ac539736a4842"));
//		System.out.println(getRandomNum(8));
//		System.out.println(validateEmail("red@zosen-tec.com"));
//		System.out.println(System.getProperty("os.name"));
	}

	public static Map<String, Object> requestParam2Map(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		Integer pageIndex = Objects.strToInt(request.getParameter("pageIndex"), 1);
		Integer pageSize = Objects.strToInt(request.getParameter("pageSize"), 10);
		map.put(MagicConstants.LIMIT_START, (pageIndex-1)*pageSize);
		map.put(MagicConstants.LIMIT_END, pageSize);
		return map;
	}


	public static Map<String,Object> pageVo2Map(int pageIndexSource, int pageSizeSource) {
		Map<String, Object> map = new HashMap<>();
		Integer pageIndex =(Integer)( pageIndexSource <= 0 ? 1: pageIndexSource);
		Integer pageSize =(Integer)( pageSizeSource <= 0 ? 10: pageSizeSource);
		map.put(MagicConstants.LIMIT_START, (pageIndex-1)*pageSize);
		map.put(MagicConstants.LIMIT_END, pageSize);
		return map;
	}

	public static String createTag() {
		String[] strArr = {"跳楼减价","卖血减价","便宜甩卖","清仓甩卖"};
		return strArr[(new Random()).nextInt(strArr.length)];
	}

	public static void setPageIndexAndPageSize(Map<String, Object> map, int pageIndexSource, int pageIndexTarget, int pageSizeSource, int pageSizeTarget) {
		Integer pageIndex =(Integer)( pageIndexSource <= 0 ? pageIndexTarget: pageIndexSource);
		Integer pageSize =(Integer)( pageSizeSource <= 0 ? pageSizeTarget: pageSizeSource);
		map.put(MagicConstants.LIMIT_START, (pageIndex-1)*pageSize);
		map.put(MagicConstants.LIMIT_END, pageSize);
	}
}


