/**
 * 初始化二维码生成详情对话框
 */
var EsOrderQrcodeInfoDlg = {
    esOrderQrcodeInfoData : {}
};

/**
 * 清除数据
 */
EsOrderQrcodeInfoDlg.clearData = function() {
    this.esOrderQrcodeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EsOrderQrcodeInfoDlg.set = function(key, val) {
    this.esOrderQrcodeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EsOrderQrcodeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
EsOrderQrcodeInfoDlg.close = function() {
    parent.layer.close(window.parent.EsOrderQrcode.layerIndex);
}

/**
 * 收集数据
 */
EsOrderQrcodeInfoDlg.collectData = function() {
    this
    .set('id')
    .set('batch')
    .set('qrcode')
    .set('orderId')
    .set('status');
}

/**
 * 提交添加
 */
EsOrderQrcodeInfoDlg.addSubmit = function() {

    this.clearData();
    this.set('qrcodeNum');

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/esOrderQrcode/add", function(data){
        Feng.success("添加成功!");
        window.parent.EsOrderQrcode.table.refresh();
        EsOrderQrcodeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.esOrderQrcodeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
EsOrderQrcodeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/esOrderQrcode/update", function(data){
        Feng.success("修改成功!");
        window.parent.EsOrderQrcode.table.refresh();
        EsOrderQrcodeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.esOrderQrcodeInfoData);
    ajax.start();
}

$(function() {
    //初始化状态选项
    $("#status").val($("#statusValue").val());
});
