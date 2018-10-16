/**
 * 初始化系统违禁品管理详情对话框
 */
var ProductProhibitedGoodsInfoDlg = {
    productProhibitedGoodsInfoData : {}
};

/**
 * 清除数据
 */
ProductProhibitedGoodsInfoDlg.clearData = function() {
    this.productProhibitedGoodsInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProductProhibitedGoodsInfoDlg.set = function(key, val) {
    this.productProhibitedGoodsInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProductProhibitedGoodsInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProductProhibitedGoodsInfoDlg.close = function() {
    parent.layer.close(window.parent.ProductProhibitedGoods.layerIndex);
}

/**
 * 收集数据
 */
ProductProhibitedGoodsInfoDlg.collectData = function() {
    this
    .set('id')
    .set('sysProductId')
    .set('name')
    .set('remark')
    .set('status');
}

/**
 * 提交添加
 */
ProductProhibitedGoodsInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    this.productProhibitedGoodsInfoData['sysProductId'] = $("#sysProductId2").val();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/productProhibitedGoods/add", function(data){
        Feng.success("添加成功!");
        window.parent.ProductProhibitedGoods.table.refresh();
        ProductProhibitedGoodsInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.productProhibitedGoodsInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ProductProhibitedGoodsInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    this.productProhibitedGoodsInfoData['sysProductId'] = $("#sysProductId3").val();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/productProhibitedGoods/update", function(data){
        Feng.success("修改成功!");
        window.parent.ProductProhibitedGoods.table.refresh();
        ProductProhibitedGoodsInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.productProhibitedGoodsInfoData);
    ajax.start();
}

$(function() {
    //初始化状态选项
    $("#status").val($("#statusValue").val());
});
