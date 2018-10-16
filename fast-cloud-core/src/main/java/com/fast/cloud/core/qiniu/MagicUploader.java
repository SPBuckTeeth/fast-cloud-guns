
package com.fast.cloud.core.qiniu;

import com.fast.cloud.core.util.ConfigUtil;
import com.fast.cloud.core.util.EncodeUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MagicUploader {

	private static int counter = 0;
	@Autowired
	private ConfigUtil configUtil;
	private Auth auth;
	private UploadManager uploadManager;
	private BucketManager bucketManager;


	@PostConstruct
	public void init() {
		auth = Auth.create(configUtil.getQiNiuAccessKey(), configUtil.getQiNiuSecretKey());
		Configuration cfg = new Configuration(Zone.zone0());
		uploadManager=new UploadManager(cfg);
		bucketManager=new BucketManager(auth, cfg);
	}

	private String getUpToken() {
		return auth.uploadToken(configUtil.getQiNiuBucketname());
	}

	private String encodingFilename(String filename) {
		return EncodeUtil.encodeMD5(filename + System.nanoTime() + counter++) + "." + filename;
	}

	public boolean delete(String filePath) {
		String sufixString=null;
		int slashIndex = filePath.indexOf(".");
		if (slashIndex >= 0) {
			sufixString =filePath.substring(slashIndex + 1);
		}
		String filename=encodingFilename(sufixString);
		try {
			bucketManager.delete(configUtil.getQiNiuBucketname(), filename);
			return true;
		} catch (QiniuException e) {
			Response r = e.response;
			System.out.println(r.toString());
		}
		return false;
	}

	public String send(String filePath,byte[] data){
		String sufixString=null;
		int slashIndex = filePath.indexOf(".");
		if (slashIndex >= 0) {
			sufixString =filePath.substring(slashIndex + 1);
		}
		String filename=encodingFilename(sufixString);
		Response res=null;
		try {
			res = uploadManager.put(data, filename, getUpToken());
			StringMap stringMap=res.jsonToMap();
			System.err.println("-------"+res.toString());
			System.err.println("---url----"+stringMap.get("key").toString());
			return configUtil.getQiNiuAccessUrl()+"/"+stringMap.get("key").toString();
		} catch (QiniuException e) {
			e.printStackTrace();
			System.out.println(e.response.toString());
		}
		return null;
	}



}
