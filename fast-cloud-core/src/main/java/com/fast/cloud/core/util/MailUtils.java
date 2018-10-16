package com.fast.cloud.core.util;

import com.fast.cloud.core.mail.Mail;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 邮件发送工具实现类
 *
 * @author shadow
 * @create 2013/07/12
 */
public class MailUtils {

    protected final static Logger logger = Logger.getLogger(MailUtils.class);

    public static void send(HtmlEmail email, Mail mail, Map root, String tpl , MultipartFile multFile) {
        // 发送email
        try {
            // 这里是SMTP发送服务器的名字：163的如下："smtp.163.cod"
            email.setHostName(CommonUtils.HOST);
            // 这里是SMTP发送服务器的端口：163的如下："25"
            email.setSmtpPort(CommonUtils.PORT);
            // 字符编码集的设置
            email.setCharset(Mail.ENCODEING);
            // 收件人的邮箱
            email.addTo(mail.getTos());
            // 发送人的邮箱
            email.addBcc(mail.getBccs());
            // 发送人的邮箱
            email.setFrom(CommonUtils.FROM, CommonUtils.SEND_NAME);
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
            email.setAuthentication(CommonUtils.USER_NAME, CommonUtils.PASS_WORD);
            // 要发送的邮件主题
            email.setSubject(mail.getSubject());
            // 附件
            File attachment = null;
            if(multFile != null) {
            	MimeMultipart multipart = new MimeMultipart();
            	BodyPart attchmentPart = new MimeBodyPart();
            	// 将MultipartFile 转为 File
            	attachment = multipartToFile(multFile);
                DataSource source = new FileDataSource(attachment);
                attchmentPart.setDataHandler(new DataHandler(source));
                attchmentPart.setFileName(MimeUtility.encodeText(multFile.getOriginalFilename()));
                multipart.addBodyPart(attchmentPart);
                email.addPart(multipart);
            }

            StringBuffer bodyBf = new StringBuffer();
            bodyBf.append("");
            email.addPart(bodyBf.toString(), "text/html;charset=utf-8");

            if(StringUtils.isNotBlank(tpl)) {
                email.setHtmlMsg(htmlTemplate(root, tpl));
            }
            

            // 发送
            String result = email.send();
            System.out.println("result : ----------------------" + result);
            if(attachment != null) {
                deleteFile(attachment);
            }

        } catch (EmailException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String htmlTemplate(Map root, String tpl) throws Exception {
        Configuration cfg = new Configuration();

//        cfg.setDirectoryForTemplateLoading(new File(CommonUtils.PROJECT_REAL_PATH + "//WEB-INF//templates//"));
//		cfg.setServletContextForTemplateLoading(request.getServletContext(), "WEB-INF/templates");
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
        ////////////////////////////////////////////////////////////12-5更改，只更改了此处ymh
       // cfg.setDirectoryForTemplateLoading(new File(Thread.currentThread().getContextClassLoader().getResource("").getPath()));
        cfg.setDirectoryForTemplateLoading(new File(CommonUtils.PROJECT_REAL_PATH+"//WEB-INF//templates//"));
        ////////////////////////////////////////////////////////
        Template t = cfg.getTemplate(tpl, "utf-8");

        //最关键在这里，不使用与文件相关的Writer
        StringWriter stringWriter = new StringWriter();
        try {
            t.process(root, stringWriter);
            //这里打印的就是通过模板处理后得到的字符串内容
//            System.out.println(stringWriter.toString());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }
    public static String htmlTemplates(HttpServletRequest request, Map root, String tpl) throws Exception {
        Configuration cfg = new Configuration();

        cfg.setServletContextForTemplateLoading(request.getSession().getServletContext(), "/templates");
//        cfg.setDirectoryForTemplateLoading(new File(CommonUtils.PROJECT_REAL_PATH + "//WEB-INF//templates//"));
//		cfg.setServletContextForTemplateLoading(request.getServletContext(), "WEB-INF/templates");
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
        System.out.println(request.getSession().getServletContext());
        ////////////////////////////////////////////////////////////12-5更改，只更改了此处ymh
       // cfg.setDirectoryForTemplateLoading(new File(Thread.currentThread().getContextClassLoader().getResource("").getPath()));
//        cfg.setDirectoryForTemplateLoading(new File(CommonUtils.PROJECT_REAL_PATH+"//templates//"));
        ////////////////////////////////////////////////////////
        Template t = cfg.getTemplate(tpl, "utf-8");

        //最关键在这里，不使用与文件相关的Writer
        StringWriter stringWriter = new StringWriter();
        try {
            t.process(root, stringWriter);
            //这里打印的就是通过模板处理后得到的字符串内容
//            System.out.println(stringWriter.toString());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
        testMail();
    }

    private static void testMail() {
//        String[] recipientEmails = {"462428369@qq.com"};
    	String[] recipientEmails = {"caodashen@163.com"};
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("userName", "小哪吒");
        contentMap.put("bookingTime", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
        contentMap.put("phone", "110110");
        contentMap.put("serialNo", "2017112322222");
        MailUtils.sendMail(null, "卡果先生", recipientEmails, String.format("客户%s订舱了", "小哪吒"), contentMap, "table_price_excel.tpl");
    }

    /**
     * @param senderEmail--发件人邮箱
     * @param sender--发件人昵称
     * @param recipientEmails--收件人邮箱
     * @param subject--标题
     * @param contentMap--消息map
     * @param tpl--模板
     * @return
     */
    public static Boolean sendMail(String senderEmail, String sender, String[] recipientEmails, String subject, Map<String, Object> contentMap, String tpl) {
        Mail mail = new Mail();
        mail.setSender(sender);
        mail.setTos(recipientEmails); // 接收人
        mail.setBccs(recipientEmails);
        mail.setSubject(subject);
        HtmlEmail email = new HtmlEmail();
        try {

/*            String imageUrl = "http://od30d1cbk.bkt.clouddn.com/cargo-small.jpg";
            String imageName = "headImg";
            URL url = new URL(imageUrl);
            URLConnection urlc;
            try {
                urlc = url.openConnection();
                urlc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String cid = email.embed(url, imageName);
            contentMap.put("headImage", cid);*/

            MailUtils.send(email, mail, contentMap, tpl , null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * @param sender--发件人昵称
     * @param recipientEmails--收件人邮箱
     * @param subject--标题
     * @param contentMap--消息map
     * @param tpl--模板
     * @return
     */
    public static Boolean sendMail(String sender, String[] recipientEmails, String subject, Map<String, Object> contentMap, String tpl , MultipartFile multFile) {
        Mail mail = new Mail();
        mail.setSender(sender);
        mail.setTos(recipientEmails); // 接收人
        mail.setBccs(recipientEmails);
        mail.setSubject(subject);
        HtmlEmail email = new HtmlEmail();
        try {
            MailUtils.send(email, mail, contentMap, tpl , multFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    
    /** 
     * MultipartFile 转换成File 
     *  
     * @param multfile 原文件类型 
     * @return File 
     * @throws IOException 
     */  
    private static File multipartToFile(MultipartFile multfile) throws IOException {
    	String fileName = multfile.getOriginalFilename();
        // 获取文件后缀
        String prefix=fileName.substring(fileName.lastIndexOf("."));
        String fileN=fileName.substring(0 ,fileName.lastIndexOf("."));
        // 用uuid作为文件名，防止生成的临时文件重复
        String tempFilePath="D:\\";
        File tempDir = new File( tempFilePath );
        final File excelFile = File.createTempFile(fileN , prefix);
        multfile.transferTo(excelFile);
        return excelFile;
    }  
    
    /**  
     * 删除  
     *   
     * @param files  
     */  
    private static void deleteFile(File... files) {  
        for (File file : files) {
            if (file.exists()) {  
                logger.info(file.getPath());
                file.delete();  
            }  
        }  
    }
}

