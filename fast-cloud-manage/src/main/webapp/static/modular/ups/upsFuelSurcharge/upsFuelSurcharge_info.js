/**
 * 初始化燃油附加费管理详情对话框
 */
var UpsFuelSurchargeInfoDlg = {
    upsFuelSurchargeInfoData : {}
};

/**
 * 清除数据
 */
UpsFuelSurchargeInfoDlg.clearData = function() {
    this.upsFuelSurchargeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UpsFuelSurchargeInfoDlg.set = function(key, val) {
    this.upsFuelSurchargeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UpsFuelSurchargeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
UpsFuelSurchargeInfoDlg.close = function() {
    parent.layer.close(window.parent.UpsFuelSurcharge.layerIndex);
}

/**
 * 收集数据
 */
UpsFuelSurchargeInfoDlg.collectData = function() {
    this
    .set('id')
    .set('effectiveDate')
    .set('surcharge')
    .set('status');
}

/**
 * 提交添加
 */
UpsFuelSurchargeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/upsFuelSurcharge/add", function(data){
        Feng.success("添加成功!");
        window.parent.UpsFuelSurcharge.table.refresh();
        UpsFuelSurchargeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.upsFuelSurchargeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
UpsFuelSurchargeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/upsFuelSurcharge/update", function(data){
        Feng.success("修改成功!");
        window.parent.UpsFuelSurcharge.table.refresh();
        UpsFuelSurchargeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.upsFuelSurchargeInfoData);
    ajax.start();
}

$(function() {
    //初始化状态选项
    $("#status").val($("#statusValue").val());
});
