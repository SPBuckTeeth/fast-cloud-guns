package com.fast.cloud.modular.sysproduct.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.fast.cloud.core.exception.GunsException;
import com.fast.cloud.core.log.LogObjectHolder;
import com.fast.cloud.modular.sysproduct.model.Product;
import com.fast.cloud.core.base.controller.BaseController;
import com.fast.cloud.core.common.constant.factory.PageFactory;
import com.fast.cloud.core.util.Contant;
import com.fast.cloud.core.util.ResponseUtil;
import com.fast.cloud.modular.sysproduct.exception.ProductExceptionEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.fast.cloud.modular.sysproduct.service.IProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 系统产品管理控制器
 *
 * @author lossdate
 * @Date 2018-10-08 13:43:13
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

    private String PREFIX = "/sysproduct/product/";

    @Autowired
    private IProductService productService;

    /**
     * 跳转到系统产品管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "product.html";
    }

    /**
     * 跳转到添加系统产品管理
     */
    @RequestMapping("/product_add")
    public String productAdd() {
        return PREFIX + "product_add.html";
    }

    /**
     * 跳转到修改系统产品管理
     */
    @RequestMapping("/product_update/{productId}")
    public String productUpdate(@PathVariable String productId, Model model) {
        Product product = productService.selectById(productId);
        model.addAttribute("item",product);
        LogObjectHolder.me().set(product);
        return PREFIX + "product_edit.html";
    }

    /**
     * 获取系统产品管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String beginTime,
                       @RequestParam(required = false) String endTime,
                       @RequestParam(required = false) String enterpriseName,
                       @RequestParam(required = false) String servicePlatform,
                       @RequestParam(required = false) String status) {
        Page<Product> page = new PageFactory<Product>().defaultPage();
        String orderByField = page.getOrderByField();
        if(!StringUtils.isBlank(orderByField)) {
            orderByField = Contant.commonStringUtil(orderByField);
            page.setOrderByField(orderByField);
        }

        List<Product> result = productService.getProductList(page, beginTime, endTime, enterpriseName, servicePlatform, status, page.getOrderByField(), page.isAsc());
        page.setRecords(result);
        return super.packForBT(page);
    }

    /**
     * 新增系统产品管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Product product) {
        long count = productService.checkRepetition(product);
        if(count > 0) {
            throw new GunsException(ProductExceptionEnum.EXISTED);
        } else {
            productService.insert(product);
            return SUCCESS_TIP;
        }
    }

    /**
     * 删除系统产品管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String productId) {
        productService.deleteById(productId);
        return SUCCESS_TIP;
    }

    /**
     * 修改系统产品管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Product product) {
        long count = productService.checkRepetition(product);
        if(count > 0) {
            throw new GunsException(ProductExceptionEnum.EXISTED);
        } else {
            productService.updateById(product);
            return SUCCESS_TIP;
        }
    }

    /**
     * 系统产品管理详情
     */
    @RequestMapping(value = "/detail/{productId}")
    @ResponseBody
    public Object detail(@PathVariable("productId") String productId) {
        return productService.selectById(productId);
    }

    /**
     * 所有产品列表
     */
    @RequestMapping(value = "productList")
    @ResponseBody
    public Object mobileCodeList(HttpServletRequest request) {
        return ResponseUtil.ok(productService.selectList(null));
    }
}
