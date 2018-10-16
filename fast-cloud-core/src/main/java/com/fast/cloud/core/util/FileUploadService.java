package com.fast.cloud.core.util;

import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Component
public class FileUploadService {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private ConfigUtil configUtil;

	// 默认的文件名最大长度
	public static final int DEFAULT_FILE_NAME_LENGTH = 200;
	public static final String[] IMAGE_EXTENSION = { "bmp", "gif", "jpg", "jpeg", "png" };
	public static final String[] FLASH_EXTENSION = { "swf", "flv" };
	public static final String[] MEDIA_EXTENSION = { "swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg", "asf", "rm", "rmvb" };
	public static final String[] DEFAULT_ALLOWED_EXTENSION = { "bmp", "gif", "jpg", "jpeg", "png", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm",
			"txt", "rar", "zip", "gz", "bz2", "pdf" };

	private static int counter = 0;

	public String[] uploadPost(HttpServletRequest request, MultipartFile iamgefile) {
		String[] licenceImageUrl = null;
		try {
			if (iamgefile != null) {
				licenceImageUrl = upload(request, iamgefile, DEFAULT_ALLOWED_EXTENSION);
			}
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException", e);
		} catch (FileSizeLimitExceededException e) {
			logger.error("FileSizeLimitExceededException", e);
		} catch (InvalidExtensionException e) {
			logger.error("InvalidExtensionException", e);
		} catch (FileNameLengthLimitExceededException e) {
			logger.error("FileNameLengthLimitExceededException", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return licenceImageUrl;
	}

	public Response upload(HttpServletRequest request, MultipartFile licenceImage) {
		String[] licenceImageUrl = null;
		try {
			if (licenceImage != null) {
				licenceImageUrl = upload(request, licenceImage, DEFAULT_ALLOWED_EXTENSION);
			}
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException", e);
			return ResponseUtil.error(ErrorCode.FILE_NOT_FOUNT);
		} catch (FileSizeLimitExceededException e) {
			logger.error("FileSizeLimitExceededException", e);
			return ResponseUtil.error(ErrorCode.FILE_SIZE_LIMIT_EXCEEDED);
		} catch (InvalidExtensionException e) {
			logger.error("InvalidExtensionException", e);
			return ResponseUtil.error(ErrorCode.INVALID_EXTENSION);
		} catch (FileNameLengthLimitExceededException e) {
			logger.error("FileNameLengthLimitExceededException", e);
			return ResponseUtil.error(ErrorCode.FILE_NAME_LENGTH_LIMIT_EXCEEDED);
		} catch (IOException e) {
			logger.error("IOException", e);
			return ResponseUtil.error(ErrorCode.INTERNAL_ERROR);
		} catch (Exception e) {
			logger.error("Exception", e);
			return ResponseUtil.error(ErrorCode.INTERNAL_ERROR);
		}
		if (licenceImageUrl == null || licenceImageUrl[1] == null) {
			return ResponseUtil.error();
		}
		return ResponseUtil.ok(licenceImageUrl[1]);
	}

	/**
	 * 以默认配置进行文件上传
	 *
	 * @param request
	 *            当前请求
	 * @param file
	 *            上传的文件
	 * @param allowedExtension
	 *            允许上传的文件类型
	 * @return
	 * @throws Exception
	 */
	public final String[] upload(HttpServletRequest request, MultipartFile file, String[] allowedExtension) throws Exception {
		return upload(request, configUtil.getUploadAbsoluteFilePath(), null, configUtil.getUploadAccessFilePath(), file, allowedExtension,
				configUtil.getUploadFileMaxSize(), true);
	}

	/**
	 * 以默认配置进行文件上传
	 *
	 * @param request
	 *            当前请求
	 * @param file
	 *            上传的文件
	 * @param allowedExtension
	 *            允许上传的文件类型
	 * @return
	 * @throws Exception
	 */
	public final String[] upload(HttpServletRequest request, String absoluteBaseDir, MultipartFile file, String[] allowedExtension) throws Exception {
		return upload(request, configUtil.getUploadAbsoluteFilePath(), absoluteBaseDir, configUtil.getUploadAccessFilePath(), file, allowedExtension,
				configUtil.getUploadFileMaxSize(), true);
	}

	/**
	 * 文件上传
	 *
	 * @param request
	 *            当前请求 从请求中提取 应用上下文根
	 * @param baseDir
	 *            相对应用的基目录
	 * @param file
	 *            上传的文件
	 * @param allowedExtension
	 *            允许的文件类型 null 表示允许所有
	 * @param maxSize
	 *            最大上传的大小 -1 表示不限制
	 * @param needDatePathAndRandomName
	 *            是否需要日期目录和随机文件名前缀
	 * @return 返回上传成功的文件名
	 * @throws InvalidExtensionException
	 *             如果MIME类型不允许
	 * @throws FileSizeLimitExceededException
	 *             如果超出最大大小
	 * @throws FileNameLengthLimitExceededException
	 *             文件名太长
	 * @throws IOException
	 *             比如读写文件出错时
	 */
	public final String[] upload(HttpServletRequest request, String baseDir, String absoluteBaseDir, String accessFilePath, MultipartFile file,
                                 String[] allowedExtension, long maxSize, boolean needDatePathAndRandomName) throws InvalidExtensionException, FileSizeLimitExceededException,
			IOException, FileNameLengthLimitExceededException, Exception {
		int fileNamelength = file.getOriginalFilename().length();
		if (fileNamelength > DEFAULT_FILE_NAME_LENGTH) {
			throw new FileNameLengthLimitExceededException(file.getOriginalFilename(), fileNamelength, DEFAULT_FILE_NAME_LENGTH);
		}
		assertAllowed(file, allowedExtension, maxSize);
		String[] filenames = extractFilename(file, baseDir, absoluteBaseDir, accessFilePath, needDatePathAndRandomName);
		File desc = getAbsoluteFile(filenames[0]);
		file.transferTo(desc);
		return filenames;
	}

	private final File getAbsoluteFile(String filename) throws IOException {
		File desc = new File(filename);
		if (!desc.getParentFile().exists()) {
			desc.getParentFile().mkdirs();
		}
		if (!desc.exists()) {
			desc.createNewFile();
		}
		return desc;
	}

	public final String[] extractFilename(MultipartFile file, String baseDir, String absoluteBaseDir, String accessFilePath, boolean needDatePathAndRandomName)
			throws UnsupportedEncodingException {
		String[] filenames = new String[2];
		filenames[0] = file.getOriginalFilename();
		int slashIndex = filenames[0].indexOf(".");
		if (slashIndex >= 0) {
			filenames[0] = filenames[0].substring(slashIndex + 1);
		}
		String encodingFilename = encodingFilename(filenames[0]);
		if (needDatePathAndRandomName) {
			String datePath = datePath();
			if (StringUtils.isEmpty(absoluteBaseDir)) {
				filenames[0] = baseDir + File.separator + datePath + File.separator + encodingFilename;
			} else {
				filenames[0] = baseDir + File.separator + absoluteBaseDir + File.separator + datePath + File.separator + encodingFilename;
			}
			filenames[1] = accessFilePath + File.separator + datePath + File.separator + encodingFilename;
		} else {
			if (StringUtils.isEmpty(absoluteBaseDir)) {
				filenames[0] = baseDir + File.separator + encodingFilename;
			} else {
				filenames[0] = baseDir + File.separator + absoluteBaseDir + File.separator + encodingFilename;
			}
			filenames[1] = accessFilePath + File.separator + encodingFilename;
		}
		filenames[0] = filenames[0].replace('\\', '/');
		filenames[1] = filenames[1].replace('\\', '/');
		return filenames;
	}

	/**
	 * 编码文件名,默认格式为： 1、'_'替换为 ' ' 2、hex(md5(filename + now nano time + counter++)) 3、[2]_原始文件名
	 */
	private final String encodingFilename(String filename) {
		filename = EncodeUtil.encodeMD5(filename + System.nanoTime() + counter++) + "." + filename;
		return filename;
	}

	/**
	 * 日期路径 即年/月/日 如2013/01/03
	 */
	private final String datePath() {
		Date now = new Date();
		return DateFormatUtils.format(now, "yyyy/MM/dd");
	}

	/**
	 * 是否允许文件上传
	 * 
	 * @param file
	 *            上传的文件
	 * @param allowedExtension
	 *            文件类型 null 表示允许所有
	 * @param maxSize
	 *            最大大小 字节为单位 -1表示不限制
	 * @return
	 * @throws InvalidExtensionException
	 *             如果MIME类型不允许
	 * @throws FileSizeLimitExceededException
	 *             如果超出最大大小
	 */
	public final void assertAllowed(MultipartFile file, String[] allowedExtension, long maxSize) throws InvalidExtensionException,
			FileSizeLimitExceededException {
		String filename = file.getOriginalFilename();
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
			if (allowedExtension == IMAGE_EXTENSION) {
				throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension, filename);
			} else if (allowedExtension == FLASH_EXTENSION) {
				throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension, filename);
			} else if (allowedExtension == MEDIA_EXTENSION) {
				throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension, filename);
			} else {
				throw new InvalidExtensionException(allowedExtension, extension, filename);
			}
		}
		long size = file.getSize();
		if (maxSize != -1 && size > maxSize) {
			throw new FileSizeLimitExceededException("not allowed upload upload", size, maxSize);
		}
	}

	/**
	 * 判断MIME类型是否是允许的MIME类型
	 */
	public final boolean isAllowedExtension(String extension, String[] allowedExtension) {
		for (String str : allowedExtension) {
			if (str.equalsIgnoreCase(extension)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 提取上传的根目录 默认是应用的根
	 *
	 * @param request
	 * @return
	 */
	public final String extractUploadDir(HttpServletRequest request) {
		return request.getServletContext().getRealPath("/");
	}

	public final void delete(HttpServletRequest request, String filename) throws IOException {
		if (StringUtils.isEmpty(filename)) {
			return;
		}
		new File(configUtil.getUploadAbsoluteFilePath() + filename).deleteOnExit();
	}

}