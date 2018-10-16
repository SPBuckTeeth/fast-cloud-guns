package com.fast.cloud.core.util;


import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 文字转换成图片
 * */

public class FontToImgUtil {

 BufferedImage image;

 void createImage(String fileLocation) {
  try {
   FileOutputStream fos = new FileOutputStream(fileLocation);
   BufferedOutputStream bos = new BufferedOutputStream(fos);
   JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
   encoder.encode(image);
   bos.close();
  } catch (Exception e) {
   e.printStackTrace();
  }
 }

 public void graphicsGeneration(String imgurl,String str) {

  int width = 800;// 图片的宽度

  int imageHeight = 250;// 图片的高度

  image = new BufferedImage(width, imageHeight,
    BufferedImage.TYPE_INT_RGB);
  Graphics graphics = image.getGraphics();
//  graphics.setColor(Color.blue);
  graphics.fillRect(0, 0, width, imageHeight);
  graphics.setColor(Color.black);
  graphics.setFont(new Font("Arial", 0,31));
//  String str="烈日秋霜，忠肝义胆，千载家谱。得姓何年，细参辛字，一笑君听取。艰辛做就，悲辛滋味，总是辛酸辛苦。更十分，向人辛辣，椒桂捣残堪吐。世间应有，芳甘浓美，不到吾家门户。比著儿曹，累累却有，金印光垂组。付君此事，从今直上，休忆对床风雨。但赢得，靴纹绉面，记余戏语。";

List list=new ArrayList();
int n=30;
String s=null;
int t=0;
int a=str.length()/n+1;
Font font = new Font("Arial", 0, 31);// 新建字体

// 这一段是求出整个要转化为图片的内容之像素大小
Rectangle2D r = font.getStringBounds(str, new FontRenderContext(
AffineTransform.getScaleInstance(1, 1), false, false));// 根据字体大小新建矩形
int unitHeight = (int) Math.floor(r.getHeight());// 获取单个字符的高度
// 获取整个str用了font样式的宽度这里用四舍五入后+1保证宽度绝对能容纳这个字符串作为图片的宽度
//int width = (int) Math.round(r.getWidth()) + 1;
int height = unitHeight + 3;
int h1=0;
/*for(int i=0;i<a;i++){
if(str.length()>0){
t=str.length()>n?n:str.length();
s=str.substring(0, t);
str=str.substring(t);
}
System.out.println(s);

int h=h1+height;
graphics.drawString(s, 0,h);
h1=h;
}*/

String strs[]=str.split("\\-\\|\\-");
for(int i=0;i<strs.length;i++){

      int h=h1+height;
graphics.drawString(strs[i],10, h);
h1=h;

}



// 改成这样:
  BufferedImage bimg = null;
  try {
   bimg = javax.imageio.ImageIO.read(new java.io.File(imgurl));
  } catch (Exception e) {
  }

  if (bimg != null)
   graphics.drawImage(bimg, 230, 0, null);
  graphics.dispose();

  createImage(imgurl);

 }

/* public static void main(String[] args) {
  FontToImgUtil cg = new FontToImgUtil();
  try {
   cg.graphicsGeneration("D://2.jpg");
  } catch (Exception e) {
   e.printStackTrace();
  }
 }*/
}
