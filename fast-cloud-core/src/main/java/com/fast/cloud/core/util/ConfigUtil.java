package com.fast.cloud.core.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigUtil {
	
	/**
	 * 上传文件存储的绝对路径
	 */
	@Value("${upload.absolute.file.path}")
	private String uploadAbsoluteFilePath;
	/**
	 * 访问的base路径
	 */
	@Value("${upload.access.file.path}")
	private String uploadAccessFilePath;

	/**
	 * 默认大小 50M
	 */
	@Value("${upload.file.max.size}")
	private Long uploadFileMaxSize;

	@Value("${download.absolute.file.path}")
	private String downloadAbsoluteFilePath;
	
	@Value("${qiniu.access_Key}")
	private String qiNiuAccessKey;
	@Value("${qiniu.secret_Key}")
	private String qiNiuSecretKey;
	@Value("${qiniu.bucketname}")
	private String qiNiuBucketname;
	@Value("${qiniu.access_url}")
	private String qiNiuAccessUrl;
	
	@Value("${user.default.password}")
	private String userDefaultPassword;
	
	
	public String getUserDefaultPassword() {
		return userDefaultPassword;
	}

	public void setUserDefaultPassword(String userDefaultPassword) {
		this.userDefaultPassword = userDefaultPassword;
	}

	public String getQiNiuAccessUrl() {
		return qiNiuAccessUrl;
	}

	public String getQiNiuAccessKey() {
		return qiNiuAccessKey;
	}

	public String getQiNiuSecretKey() {
		return qiNiuSecretKey;
	}

	public String getQiNiuBucketname() {
		return qiNiuBucketname;
	}

	public String getDownloadAbsoluteFilePath() {
		return downloadAbsoluteFilePath;
	}

	public Long getUploadFileMaxSize() {
		return uploadFileMaxSize;
	}

	public String getUploadAbsoluteFilePath() {
		return uploadAbsoluteFilePath;
	}

	public String getUploadAccessFilePath() {
		return uploadAccessFilePath;
	}

	public String getJpushMasterSecret() {
		return null;
	}

	public String getJpushAppKey() {
		return null;
	}

}
