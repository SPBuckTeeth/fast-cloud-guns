/**
 * 二维码生成管理初始化
 */
var EsOrderQrcode = {
    id: "EsOrderQrcodeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
EsOrderQrcode.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '二维码', field: 'qrcode', visible: true, align: 'center', valign: 'middle',formatter: function (value, row, index) {
                return '<a href="'+value+'" target="_blank">\n' +
                    '<img src="'+value+'" style="width: 40px;height:40px;"/></a>'
            }},
            {title: '批次', field: 'batch', visible: true, align: 'center', valign: 'middle'},
            {title: '订单id', field: 'orderId', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle',formatter: function (value, row, index) {
                if(value == 1) {
                    return "已使用";
                } else {
                    return "未使用";
                }
            }, sortable: true},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '更新时间', field: 'updateTime', visible: true, align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
EsOrderQrcode.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        EsOrderQrcode.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加二维码生成
 */
EsOrderQrcode.openAddEsOrderQrcode = function () {
    var index = layer.open({
        type: 2,
        title: '添加二维码生成',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/esOrderQrcode/esOrderQrcode_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看二维码生成详情
 */
EsOrderQrcode.openEsOrderQrcodeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '二维码生成详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/esOrderQrcode/esOrderQrcode_update/' + EsOrderQrcode.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除二维码生成
 */
EsOrderQrcode.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/esOrderQrcode/delete", function (data) {
            Feng.success("删除成功!");
            EsOrderQrcode.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("esOrderQrcodeId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询二维码生成列表
 */
EsOrderQrcode.search = function () {
    var queryData = {};

    queryData['batch'] = $("#batch").val();
    queryData['orderId'] = $("#orderId").val();
    queryData['status'] = $("#status").val();
    EsOrderQrcode.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = EsOrderQrcode.initColumn();
    var table = new BSTable(EsOrderQrcode.id, "/esOrderQrcode/list", defaultColunms);
    table.setPaginationType("server");
    EsOrderQrcode.table = table.init();
});

