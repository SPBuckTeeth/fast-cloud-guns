/**
 * 系统违禁品管理管理初始化
 */
var ProductProhibitedGoods = {
    id: "ProductProhibitedGoodsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProductProhibitedGoods.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '产品名称', field: 'sysProductName', visible: true, align: 'center', valign: 'middle', sortable: false},
            {title: '禁运品名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle',formatter: function (value, row, index) {
                if(value == 1) {
                    return "无效";
                } else {
                    return "有效";
                }
            }, sortable: true},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
            {title: '更新时间', field: 'updateTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ProductProhibitedGoods.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProductProhibitedGoods.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加系统违禁品管理
 */
ProductProhibitedGoods.openAddProductProhibitedGoods = function () {
    var index = layer.open({
        type: 2,
        title: '添加系统违禁品管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/productProhibitedGoods/productProhibitedGoods_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看系统违禁品管理详情
 */
ProductProhibitedGoods.openProductProhibitedGoodsDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '系统违禁品管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/productProhibitedGoods/productProhibitedGoods_update/' + ProductProhibitedGoods.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除系统违禁品管理
 */
ProductProhibitedGoods.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/productProhibitedGoods/delete", function (data) {
            Feng.success("删除成功!");
            ProductProhibitedGoods.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("productProhibitedGoodsId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询系统违禁品管理列表
 */
ProductProhibitedGoods.search = function () {
    var queryData = {};
    queryData['sysProductId'] = $("#sysProductId").val();
    queryData['name'] = $("#name").val();
    ProductProhibitedGoods.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ProductProhibitedGoods.initColumn();
    var table = new BSTable(ProductProhibitedGoods.id, "/productProhibitedGoods/list", defaultColunms);
    table.setPaginationType("server");
    ProductProhibitedGoods.table = table.init();
});
