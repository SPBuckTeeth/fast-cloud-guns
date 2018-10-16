/**
 * 燃油附加费管理管理初始化
 */
var UpsFuelSurcharge = {
    id: "UpsFuelSurchargeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
UpsFuelSurcharge.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '生效日期', field: 'effectiveDate', visible: true, align: 'center', valign: 'middle'},
            {title: '附加率(%)', field: 'surcharge', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'createTime', visible: false, align: 'center', valign: 'middle'},
            {title: '', field: 'updateTime', visible: false, align: 'center', valign: 'middle'},
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle',formatter: function (value, row, index) {
                if(value == 1) {
                    return "无效";
                } else {
                    return "有效";
                }
            }, sortable: true}
    ];
};

/**
 * 检查是否选中
 */
UpsFuelSurcharge.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        UpsFuelSurcharge.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加燃油附加费管理
 */
UpsFuelSurcharge.openAddUpsFuelSurcharge = function () {
    var index = layer.open({
        type: 2,
        title: '添加燃油附加费管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/upsFuelSurcharge/upsFuelSurcharge_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看燃油附加费管理详情
 */
UpsFuelSurcharge.openUpsFuelSurchargeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '燃油附加费管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/upsFuelSurcharge/upsFuelSurcharge_update/' + UpsFuelSurcharge.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除燃油附加费管理
 */
UpsFuelSurcharge.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/upsFuelSurcharge/delete", function (data) {
            Feng.success("删除成功!");
            UpsFuelSurcharge.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("upsFuelSurchargeId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询燃油附加费管理列表
 */
UpsFuelSurcharge.search = function () {
    var queryData = {};
    queryData['effectiveDate'] = $("#effectiveDate").val();
    queryData['surcharge'] = $("#surcharge").val();
    UpsFuelSurcharge.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = UpsFuelSurcharge.initColumn();
    var table = new BSTable(UpsFuelSurcharge.id, "/upsFuelSurcharge/list", defaultColunms);
    table.setPaginationType("server");
    UpsFuelSurcharge.table = table.init();
});
