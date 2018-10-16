package com.fast.cloud.modular.system.controller;


import com.alibaba.fastjson.JSONException;
import com.fast.cloud.core.base.controller.BaseController;
import com.fast.cloud.core.model.base.po.JstreeNode;
import com.fast.cloud.core.qiniu.MagicUploader;
import com.fast.cloud.core.util.*;
import com.fast.cloud.core.web.util.BrowserCacheGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("file")
public class FileController extends BaseController {

	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private MagicUploader magicUploader;

	private BrowserCacheGenerator browserCacheGenerator;
	

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		return "/system/file/file-list";
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public Object json(HttpServletRequest request, String parent) {
		String baseDir = "/";
		if (StringUtils.isNotBlank(parent)) {
			parent = parent.equals("#") ? "" : parent;
			baseDir += parent;
		}
		String wholeRealPath = request.getSession().getServletContext().getRealPath(baseDir);
		List<JstreeNode> list = new ArrayList<>();
		File file = new File(wholeRealPath);
		if (file.isDirectory()) {
			File[] subFiles = file.listFiles();
			if (subFiles.length > 0) {
				for (File subFile : subFiles) {
					JstreeNode jstreeNode = new JstreeNode(subFile, parent,"dmbase");
					list.add(jstreeNode);
				}
			}
		}
		return list;
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	@ResponseBody
	public Object find(HttpServletRequest request, String parent) {
		String baseDir = "/";
		if (StringUtils.isNotBlank(parent)) {
			parent = "#".equals(parent) ? "" : parent;
			baseDir += parent;
		}
		String wholeRealPath = request.getSession().getServletContext().getRealPath(baseDir);
		List<JstreeNode> list = new ArrayList<>();
		File file = new File(wholeRealPath);
		if (file.isDirectory()) {
			File[] subFiles = file.listFiles();
			if (subFiles.length > 0) {
				for (File subFile : subFiles) {
					JstreeNode jstreeNode = new JstreeNode(subFile, parent,"dmbase");
					list.add(jstreeNode);
				}
			}
		}
		return ResponseUtil.ok(list);
	}
	
	//@Value("${browser.cache.seconds}")
	private Integer expireSeconds=3600;
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/generic/{groupId:g\\d+}/{f1:M\\d+}/{f2:\\w{2}}/{f3:\\w{2}}/{f4:.*}" })
	@ResponseBody
	public byte[] showGenericResource(@PathVariable("groupId") String groupId, @PathVariable("f1") String f1, @PathVariable("f2") String f2, @PathVariable("f3") String f3, @PathVariable("f4") String f4, HttpServletRequest request, HttpServletResponse response) {
		try {
			String resourcePath = new StringBuilder().append(groupId).append("/").append(f1).append("/").append(f2).append("/").append(f3).append("/").append(f4).toString();
			browserCacheGenerator.checkAndPrepare(request, response, expireSeconds, true);
			return new byte[0];
		} catch (Exception e) {
			//throw new NoSuchRequestHandlingMethodException(request);
			return new byte[0];
		}
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/350x350/{groupId:g\\d+}/{f1:M\\d+}/{f2:\\w{2}}/{f3:\\w{2}}/{f4:.*}" } )
	@ResponseBody
	public byte[] show350x350Image(@PathVariable("groupId") String groupId, @PathVariable("f1") String f1, @PathVariable("f2") String f2, @PathVariable("f3") String f3, @PathVariable("f4") String f4, HttpServletRequest request, HttpServletResponse response)  {
		try {
			String resourcePath = new StringBuilder().append(groupId).append("/").append(f1).append("/").append(f2).append("/").append(f3).append("/").append(f4).toString();
			browserCacheGenerator.checkAndPrepare(request, response, expireSeconds, true);
			return new byte[0];
		} catch (Exception e) {
			//throw new NoSuchRequestHandlingMethodException(request);
			return new byte[0];
		}
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/160x160/{groupId:g\\d+}/{f1:M\\d+}/{f2:\\w{2}}/{f3:\\w{2}}/{f4:.*}" } )
	@ResponseBody
	public byte[] show160x160Image(@PathVariable("groupId") String groupId, @PathVariable("f1") String f1, @PathVariable("f2") String f2, @PathVariable("f3") String f3, @PathVariable("f4") String f4, HttpServletRequest request, HttpServletResponse response)  {
		try {
			browserCacheGenerator.checkAndPrepare(request, response, expireSeconds, true);
			return new byte[0];
		} catch (Exception e) {
			//throw new NoSuchRequestHandlingMethodException(request);
			return new byte[0];
		}
	}
	
	@RequestMapping("uploadImage")
	public Response uploadImage(MultipartFile imageFile, HttpServletRequest request, HttpServletResponse response) {
		try {
			String[] string = fileUploadService.upload(request, imageFile, FileUploadService.IMAGE_EXTENSION);
			return ResponseUtil.ok(string[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseUtil.error(ErrorCode.INTERNAL_ERROR);
	}

	@RequestMapping(method = { RequestMethod.DELETE }, value = { "/{groupId:g\\d+}/{f1:M\\d+}/{f2:\\w{2}}/{f3:\\w{2}}/{f4:.*}" })
	@ResponseBody
	public void deleteResource(@PathVariable("groupId") String groupId, @PathVariable("f1") String f1, @PathVariable("f2") String f2,
                               @PathVariable("f3") String f3, @PathVariable("f4") String f4, HttpServletRequest request) {
		String resourcePath = new StringBuilder().append(groupId).append("/").append(f1).append("/").append(f2).append("/").append(f3).append("/")
				.append(f4).toString();
	}

	@RequestMapping("iframeUpload")
	public void uploadFeedBackFile(MultipartFile imageFile, HttpServletRequest request, HttpServletResponse response) {
		ServletOutputStream writer = null;
		try {
			writer = response.getOutputStream();
			if (imageFile == null) {
				writer.println("<script>parent.imageCallback('',false)</script>");
			} else {
				String[] string = fileUploadService.upload(request, imageFile, FileUploadService.IMAGE_EXTENSION);
				writer.println("<script>parent.imageCallback('" + URLEncoder.encode(string[0], "UTF-8") + "',true)</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (writer != null) {
					writer.println("<script>parent.imageCallback('',false)</script>");
				}
			} catch (IOException e1) {
			}
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (Exception e2) {
			}
		}
	}
	
	@RequestMapping("iframeUploadFile")
	public void iframeUploadFile(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		ServletOutputStream writer = null;
		try {
			writer = response.getOutputStream();
			if (file == null) {
				writer.println("<script>parent.fileCallback('',false)</script>");
			} else {
				System.out.println(file.getOriginalFilename());
				String string = magicUploader.send(file.getOriginalFilename(),file.getBytes());
				writer.println("<script>parent.fileCallback('" + URLEncoder.encode(string, "UTF-8") + "',true)</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (writer != null) {
					writer.println("<script>parent.fileCallback('',false)</script>");
				}
			} catch (IOException e1) {
			}
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (Exception e2) {
			}
		}
	}
	
	@RequestMapping("iframeUploadTwo")
	public void iframeUploadTwoBackFile(MultipartFile imageFile, HttpServletRequest request, HttpServletResponse response) {
		ServletOutputStream writer = null;
		try {
			writer = response.getOutputStream();
			if (imageFile == null) {
				writer.println("<script>parent.imageCallback('',false)</script>");
			} else {
				System.out.println(imageFile.getOriginalFilename());
				String string = magicUploader.send(imageFile.getOriginalFilename(),imageFile.getBytes());
				writer.println("<script>parent.imageCallback('" + URLEncoder.encode(string, "UTF-8") + "',true)</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (writer != null) {
					writer.println("<script>parent.imageCallback('',false)</script>");
				}
			} catch (IOException e1) {
			}
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (Exception e2) {
			}
		}
	}
	
	@RequestMapping("iframeUploadThree")
	public void iframeUploadThree(MultipartFile imageFile, HttpServletRequest request, HttpServletResponse response) {
		ServletOutputStream writer = null;
		try {
			writer = response.getOutputStream();
			if (imageFile == null) {
				writer.println("<script>imageCallback('',false)</script>");
			} else {
				System.out.println(imageFile.getOriginalFilename());
				String string = magicUploader.send(imageFile.getOriginalFilename(),imageFile.getBytes());
				writer.println("<script>imageCallback('" + URLEncoder.encode(string, "UTF-8") + "',true)</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (writer != null) {
					writer.println("<script>imageCallback('',false)</script>");
				}
			} catch (IOException e1) {
			}
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (Exception e2) {
			}
		}
	}
	
	
	@RequestMapping("uploadTwo")
	@ResponseBody
	public Response uploadTwo(MultipartFile imageFile, HttpServletRequest request, HttpServletResponse response) {
		try {
			String string =magicUploader.send(imageFile.getOriginalFilename(),imageFile.getBytes());
			return ResponseUtil.ok(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseUtil.error(ErrorCode.INTERNAL_ERROR);
	}


	@RequestMapping("upload")
	@ResponseBody
	public Response upload(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		try {
			String string = magicUploader.send(file.getOriginalFilename(),file.getBytes());
			return ResponseUtil.ok(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseUtil.error(ErrorCode.INTERNAL_ERROR);
	}

	@RequestMapping(value = "uploadMuti", method = RequestMethod.POST)
	@ResponseBody
	public Response uploadMuti(MultipartHttpServletRequest iamgefile, String username, Boolean hasError) {
		if (hasError != null) {
			return ResponseUtil.error();
		}
		Map<String, MultipartFile> map = iamgefile.getFileMap();
		for (String filename : map.keySet()) {
			MultipartFile multipartFile = map.get(filename);
			String[] urlString = null;
			if (multipartFile != null) {
				if (multipartFile != null) {
					urlString = fileUploadService.uploadPost(iamgefile, multipartFile);
					System.out.println("urlString[" + urlString + "]");
				}
				if (urlString != null && urlString.length == 2) {
					return ResponseUtil.ok(urlString[1]);
				}
				break;
			}
		}
		return ResponseUtil.error();
	}

	@RequestMapping(value = "uploadMuti2", method = RequestMethod.POST)
	public String uploadMuti(MultipartHttpServletRequest iamgefile, String username, HttpServletRequest request) {
		List<String> list = new ArrayList<String>();
		Map<String, MultipartFile> map = iamgefile.getFileMap();
		for (String filename : map.keySet()) {
			MultipartFile multipartFile = map.get(filename);
			if (multipartFile != null) {
				String[] urlString = null;
				if (multipartFile != null) {
					urlString = fileUploadService.uploadPost(request, multipartFile);
					System.out.println("urlString[" + urlString + "]");
				}
				if (urlString != null && urlString.length == 2) {
					list.add(urlString[1]);
				}
			}
		}
//		System.out.println("username[" + username + "]");
//		request.setAttribute("images", list);
		return "zzzs/save-image";
	}

	@RequestMapping(value = "postBody")
	@ResponseBody
	public Response postBody(String username, String password, HttpServletRequest request) throws JSONException, IOException {
		StringBuffer sReturn = new StringBuffer(512);
		String sTemporary = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		while ((sTemporary = reader.readLine()) != null) {
			sReturn.append(sTemporary);
		}
		String content = IOUtils.toString(request.getInputStream());
//		System.out.println("username[" + username + "]");
//		System.out.println("content[" + content + "]");
//		System.out.println("magicHeader[" + request.getHeader("magic") + "]");
		return ResponseUtil.ok("");
	}

	@RequestMapping(value = "/app_download/{filename}")
	public void downloadApp(HttpServletRequest request, HttpServletResponse response, @PathVariable("filename") String filename) throws IOException {
		String fileSufix = "zhizaozhushou";
		Boolean targetDownload = null;
		System.out.println("filename[" + filename + "]");
		String apkPath = request.getSession().getServletContext().getRealPath("/static") + File.separator + "apk";
		File apkFile = new File(apkPath);
		String[] filenames = apkFile.list();
		if (StringUtils.isNotEmpty(filename) && filenames != null) {
			filename = filename + ".apk";
			for (String apkFilename : filenames) {
				if (apkFilename.equals(filename)) {
					targetDownload = true;
				}
			}
		} else {
			List<Float> vs_ = new ArrayList<Float>();
			for (String string : filenames) {
				String v_ = string.substring(string.indexOf('_') + 1, string.lastIndexOf('.'));
				float _v = Objects.strToFloat(v_, -1);
				if (_v > -1) {
					vs_.add(_v);
				}
			}
			Collections.sort(vs_);
			filename = fileSufix + "_" + vs_.get((vs_.size() - 1)) + ".apk";
			targetDownload = false;
		}
		if (targetDownload != null) {
			File downloadFile = new File(apkPath, filename);
			try {
				response.setContentType("application/vnd.android.package-archive");
				response.setHeader("Content-Length", String.valueOf(downloadFile.length()));
				response.setHeader("content-transfer-encoding", "binary");
				response.setHeader("Content-Disposition", "attachment; filename=" + filename);
				OutputStream outputStream = response.getOutputStream();
				outputStream.write(FileUtils.readFileToByteArray(downloadFile));
				outputStream.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/appDownload/{filename}")
	public void appDownload(HttpServletRequest request, HttpServletResponse response, @PathVariable("filename") String filename) throws IOException {
		String fileSufix = "zhizaozhushou";
		Boolean targetDownload = null;
		System.out.println("filename[" + filename + "]");
		String apkPath = request.getSession().getServletContext().getRealPath("/static") + File.separator + "weApk";
		File apkFile = new File(apkPath);
		String[] filenames = apkFile.list();
		if (StringUtils.isNotEmpty(filename) && filenames != null) {
			filename = filename + ".apk";
			for (String apkFilename : filenames) {
				if (apkFilename.equals(filename)) {
					targetDownload = true;
				}
			}
		} else {
			List<Float> vs_ = new ArrayList<Float>();
			for (String string : filenames) {
				String v_ = string.substring(string.indexOf('_') + 1, string.lastIndexOf('.'));
				float _v = Objects.strToFloat(v_, -1);
				if (_v > -1) {
					vs_.add(_v);
				}
			}
			Collections.sort(vs_);
			filename = fileSufix + "_" + vs_.get((vs_.size() - 1)) + ".apk";
			targetDownload = false;
		}
		if (targetDownload != null) {
			File downloadFile = new File(apkPath, filename);
			try {
				response.setContentType("application/vnd.android.package-archive");
				response.setHeader("Content-Length", String.valueOf(downloadFile.length()));
				response.setHeader("content-transfer-encoding", "binary");
				response.setHeader("Content-Disposition", "attachment; filename=" + filename);
				OutputStream outputStream = response.getOutputStream();
				outputStream.write(FileUtils.readFileToByteArray(downloadFile));
				outputStream.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
