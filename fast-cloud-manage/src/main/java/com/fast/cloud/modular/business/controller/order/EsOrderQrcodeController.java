package com.fast.cloud.modular.business.controller.order;

import com.baomidou.mybatisplus.plugins.Page;
import com.fast.cloud.core.common.exception.BasicExceptionEnum;
import com.fast.cloud.core.exception.GunsException;
import com.fast.cloud.core.log.LogObjectHolder;
import com.fast.cloud.core.util.FontUpQiniu;
import com.fast.cloud.core.util.QrcodeUtil;
import com.fast.cloud.core.base.controller.BaseController;
import com.fast.cloud.core.common.constant.factory.PageFactory;
import com.fast.cloud.core.util.CommonUtils;
import com.fast.cloud.core.util.Contant;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.fast.cloud.modular.business.model.order.EsOrderQrcode;
import com.fast.cloud.modular.business.service.order.IEsOrderQrcodeService;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 二维码生成控制器
 *
 * @author lossdate
 * @Date 2018-09-12 15:24:41
 */
@Controller
@RequestMapping("/esOrderQrcode")
public class EsOrderQrcodeController extends BaseController {
    private static final Logger logger = Logger.getLogger(EsOrderQrcodeController.class);

    private String PREFIX = "/business/order/esOrderQrcode/";

    @Autowired
    private IEsOrderQrcodeService esOrderQrcodeService;

    /**
     * 跳转到二维码生成首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "esOrderQrcode.html";
    }

    /**
     * 跳转到添加二维码生成
     */
    @RequestMapping("/esOrderQrcode_add")
    public String esOrderQrcodeAdd() {
        return PREFIX + "esOrderQrcode_add.html";
    }

    /**
     * 跳转到修改二维码生成
     */
    @RequestMapping("/esOrderQrcode_update/{esOrderQrcodeId}")
    public String esOrderQrcodeUpdate(@PathVariable String esOrderQrcodeId, Model model) {
        EsOrderQrcode esOrderQrcode = esOrderQrcodeService.selectById(esOrderQrcodeId);
        model.addAttribute("item",esOrderQrcode);
        LogObjectHolder.me().set(esOrderQrcode);
        return PREFIX + "esOrderQrcode_edit.html";
    }

//    /**
//     * 获取二维码生成列表
//     */
//    @RequestMapping(value = "/list")
//    @ResponseBody
//    public Object list(@RequestParam(required = false) String batch,
//                       @RequestParam(required = false) String orderId,
//                       @RequestParam(required = false) String status,
//                       HttpServletRequest request) {
////        Page<OperationLog> page = new PageFactory<OperationLog>().defaultPage();
////        List<Map<String, Object>> result = operationLogService.getOperationLogs(page, beginTime, endTime, logName, BizLogType.valueOf(logType), page.getOrderByField(), page.isAsc());
////        page.setRecords((List<OperationLog>) new LogWarpper(result).warp());
////        return super.packForBT(page);
//
//        //Map<String, Object> params = getPageMapControl(request);
//
//        Page<EsOrderQrcode> page = new PageFactory<EsOrderQrcode>().defaultPage();
//        String orderByField = page.getOrderByField();
//        if(!StringUtils.isBlank(orderByField)) {
//            orderByField = Contant.commonStringUtil(orderByField);
//            page.setOrderByField(orderByField);
//        }
//        List<EsOrderQrcode> result = esOrderQrcodeService.getEsOrderQrcode(page);
//        page.setRecords(result);
//        return super.packForBT(page);
//    }

    /**
     * 获取二维码生成列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String beginTime,
                       @RequestParam(required = false) String endTime,
                       @RequestParam(required = false) String batch,
                       @RequestParam(required = false) String orderId,
                       @RequestParam(required = false) String status) {

        Page<EsOrderQrcode> page = new PageFactory<EsOrderQrcode>().defaultPage();
        String orderByField = page.getOrderByField();
        if(!StringUtils.isBlank(orderByField)) {
            orderByField = Contant.commonStringUtil(orderByField);
            page.setOrderByField(orderByField);
        }

        List<EsOrderQrcode> result = esOrderQrcodeService.getEsOrderQrcode2(page, beginTime, endTime, batch, orderId, status, page.getOrderByField(), page.isAsc());
        page.setRecords(result);
        return super.packForBT(page);
    }

    /**
     * 新增二维码生成
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(String qrcodeNum, HttpServletRequest request) {
        if(StringUtils.isBlank(qrcodeNum)) {
            throw new GunsException(BasicExceptionEnum.NULL_NUM);
        }
        int num;
        try {
            num = Integer.parseInt(qrcodeNum);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GunsException(BasicExceptionEnum.ERROR_PATTERN);
        }

        String batch = qrcodeFactory(num, request);


        return SUCCESS_TIP;
    }

    /**
     * 删除二维码生成
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String esOrderQrcodeId) {
        esOrderQrcodeService.deleteById(esOrderQrcodeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改二维码生成
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(EsOrderQrcode esOrderQrcode) {
        esOrderQrcodeService.updateById(esOrderQrcode);
        return SUCCESS_TIP;
    }

    /**
     * 二维码生成详情
     */
    @RequestMapping(value = "/detail/{esOrderQrcodeId}")
    @ResponseBody
    public Object detail(@PathVariable("esOrderQrcodeId") String esOrderQrcodeId) {
        return esOrderQrcodeService.selectById(esOrderQrcodeId);
    }

    /**
     * 批量生成二维码
     * @param num 数量
     */
    private String qrcodeFactory(int num, HttpServletRequest request) {
        Date d = new Date();
        String batch = "ORDERQRCODE" + d.getTime();
        String qrcode;
        String id;
        List<EsOrderQrcode> orderQrcodeList = new ArrayList<>();
        //OutputStream os = null;
        ByteArrayOutputStream bos = null;
        try {

            for(int i = 0; i < num; i ++) {
                id = CommonUtils.randomID();
                EsOrderQrcode orderQrcode = new EsOrderQrcode();
                orderQrcode.setId(id);
                orderQrcode.setBatch(batch);
                orderQrcode.setStatus(0);
                orderQrcode.setCreateTime(d);
                orderQrcode.setUpdateTime(d);

                //动态地址
                //String qrURL = String.format("http://%s:%s%s%s%s", request.getServerName(), "80", request.getContextPath(), "/orderQrcode/scanOrderQrcode?qrcodeId=", id);
                //生产地址
                String qrURL = "https://fast.mrcargo.com/app-api/orderQrcode/scanOrderQrcode?qrcodeId="+id;
                //本地地址
                //String qrURL = String.format("http://%s:%s%s%s%s", "192.168.1.31", "8489", request.getContextPath(), "/orderQrcode/scanOrderQrcode?qrcodeId=", id);
                logger.debug("###### [生成二维码]qrURL === [ " + qrURL + " ] ######");

                //生成图片
                BufferedImage qrcodeImg = QrcodeUtil.encodeQrWebImg(qrURL, true);

                //二维码图片转inputStream上传七牛
                bos = new ByteArrayOutputStream();
                ImageIO.write(qrcodeImg, "jpg", bos);
                InputStream qrcodeStream = new ByteArrayInputStream(bos.toByteArray());
                qrcode = FontUpQiniu.streamUpQiniu(qrcodeStream);
                orderQrcode.setQrcode(qrcode);
                orderQrcodeList.add(orderQrcode);
            }

            if(orderQrcodeList.size() > 0) {
                //保存空白二维码到数据库
                esOrderQrcodeService.insertBatch(orderQrcodeList);
            }

        } catch (Exception e) {
            logger.debug("###### [生成二维码]getBarCode error  ######");
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return batch;
    }

}
