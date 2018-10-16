/**
 * 系统产品管理管理初始化
 */
var Product = {
    id: "ProductTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Product.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '产品logo', field: 'logo', visible: true, align: 'center', valign: 'middle',formatter: function (value, row, index) {
                return '<a href="'+value+'" target="_blank">\n' +
                    '<img src="'+value+'" style="width: 40px;height:40px;"/></a>'
            }},
            {title: '产品名称', field: 'enterpriseName', visible: true, align: 'center', valign: 'middle'},
            {title: '产品代码', field: 'servicePlatform', visible: true, align: 'center', valign: 'middle'},
            {title: '产品简介', field: 'intro', visible: true, align: 'center', valign: 'middle'},
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
Product.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Product.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加系统产品管理
 */
Product.openAddProduct = function () {
    var index = layer.open({
        type: 2,
        title: '添加系统产品管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/product/product_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看系统产品管理详情
 */
Product.openProductDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '系统产品管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/product/product_update/' + Product.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除系统产品管理
 */
Product.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/product/delete", function (data) {
            Feng.success("删除成功!");
            Product.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("productId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询系统产品管理列表
 */
Product.search = function () {
    var queryData = {};
    queryData['enterpriseName'] = $("#enterpriseName").val();
    queryData['servicePlatform'] = $("#servicePlatform").val();
    queryData['status'] = $("#status").val();
    Product.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Product.initColumn();
    var table = new BSTable(Product.id, "/product/list", defaultColunms);
    table.setPaginationType("server");
    Product.table = table.init();
});


