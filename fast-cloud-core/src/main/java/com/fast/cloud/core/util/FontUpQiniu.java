package com.fast.cloud.core.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Client;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.InputStream;

/**
 * 将用户输入的文字转换成图片上传到七牛云上
 * */
public class FontUpQiniu {
/*	@Value("${qiniu.access_Key}")
	String accessKey;
	@Value("${qiniu.secret_Key}")
	String secretKey;
	@Value("${qiniu.bucketname}")
	String bucket;
	@Value("${qiniu.access_url}")
	String accessUrl;
	*/
	/**
	 * 文字转图片上传到七牛上
	 * 
	 * */
	public String fontsUpQiniu(String str,String imgurl){
		/**
		 * 文字转换成图片
		 * */
		FontToImgUtil fontToImgUtil=new FontToImgUtil();
		fontToImgUtil.graphicsGeneration(imgurl, str);

		 Zone zone = Zone.autoZone();
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(zone);
		//...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		//...生成上传凭证，然后准备上传
		String accessKey = "ITrFj7IsSxHw60wwYCnHqiXqcEbnGxZRygp0Bh3B";
		String secretKey = "1lVP3521dR1RtifZTSerQl4jkJTRclPfn00-p1Pa";
		String bucket = "image";
		//如果是Windows情况下，格式是 D:\\qiniu\\test.png
		String localFilePath = imgurl;
		//默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = null;
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket);
		DefaultPutRet putRet=null;
		try {
		    Response response = uploadManager.put(localFilePath, key, upToken);
		    //解析上传成功的结果
		    putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
		    System.out.println(putRet.key);
		    System.out.println(putRet.hash);
		} catch (QiniuException ex) {
		    Response r = ex.response;
		    System.err.println(r.toString());
		    try {
		        System.err.println(r.bodyString());
		    } catch (QiniuException ex2) {
		        //ignore
		    }
		}
		return "http://ob2872uxx.bkt.clouddn.com/"+putRet.hash;

		
	}


	/**
	 * 文字转图片上传到七牛上
	 *
	 * */
	public static String streamUpQiniu(InputStream qrcode){
		Zone zone = Zone.autoZone();
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(zone);
		//...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		//...生成上传凭证，然后准备上传
		String accessKey = "KfOZzKOGv42XdBuQPcHi4_7yPq0cNxa_1-PYk0o-";
		String secretKey = "eo0dIiSSGJDD59SKpq36dV90-i4oZLTmX-e0nO_g";
		String bucket = "cargo";
		//如果是Windows情况下，格式是 D:\\qiniu\\test.png
		//默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = null;
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket);
		DefaultPutRet putRet=null;
		try {
			Response response = uploadManager.put(qrcode, key, upToken,null, Client.DefaultMime);
			//解析上传成功的结果
			putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			System.out.println(putRet.key);
			System.out.println(putRet.hash);
		} catch (QiniuException ex) {
			Response r = ex.response;
			System.err.println(r.toString());
			try {
				System.err.println(r.bodyString());
			} catch (QiniuException ex2) {
				//ignore
			}
		}
		return "http://od30d1cbk.bkt.clouddn.com/"+putRet.hash;


	}

	
}
