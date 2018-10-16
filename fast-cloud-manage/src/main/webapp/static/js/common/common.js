/**
 * 验证输入框
 * @param inputId
 */		
function validateInput(inputId){
	if($.trim($('#'+inputId).val()) == ''){
   		$('#'+inputId).closest(".form-group").removeClass("has-success").addClass("has-error");
		$('#'+inputId).closest(".form-group").children("div").children("span").remove();
		var message = $('#'+inputId).attr("placeholder");
   		$('#'+inputId).closest(".form-group").children("div").append("<span id=\"curl-error\" class=\"help-block m-b-none\"><i class=\"fa fa-times-circle\"></i>  "+message+"必填</span>");
   		return 1;
	}else{
   		$('#'+inputId).closest(".form-group").removeClass("has-error").addClass("has-success");
		$('#'+inputId).closest(".form-group").children("div").children("span").remove();
		return 0;
   	}
}

function iframeReload(src,isOther){
	//document.frames('iframe521942512474112000').location.reload()
	//window.parent.document
	document.getElementById("521942512474112000").src = "http://localhost:8080/magic-cloud-manage/discuss/index";
	//console.log("src---["+$("iframe[data-id='/magic-cloud-manage/discuss/index']").attr('src'));
	//$("iframe[data-id='" + src + "']").attr('src',$("iframe[data-id='" + src + "']").attr('src'));
}

/**
 * 验证输入框
 * @param inputId
 */		
function hideformGroup(inputId,hide){
	if(hide){
		$('#'+inputId).closest(".form-group").hide();
	}else{
		$('#'+inputId).closest(".form-group").show();
	}
}


var check = function (option) {
    if (option.check === true) {
        this.result = this.check(option.fvalue, option);
        return;
    }
    this.init(option);
}
check.prototype = {
    reg: {
        integer: "^[0-9]{2,26}",
        input1: "^[a-zA-Z\u4e00-\u9fa5]{2,26}",//中文或字母
        input2: "^[a-zA-Z0-9]{2,26}",//数字或字母
        input3: "^[a-zA-Z0-9\u4e00-\u9fa5]{2,26}",	//数字,中文或字母
    },
    // 功能描述：初始化
    init: function (option) {
        this.event(option);
    },
    // 功能描述：事件绑定
    event: function (option) {
        var me = this;
        option.obj.blur(function () {
            var fvalue = jQuery(this).val() || "";
            me.check(fvalue, option);
        });
        option.obj.keyup(function(){
        	var fvalue = jQuery(this).val() || "";
            me.check(fvalue, option);
        });
        option.obj.focus(function () {
            $(this).closest(".form-group").removeClass("has-error");
        });
    },
    // 功能描述：check
    check: function (fvalue, option) {
    	var regCode;
        if (this.reg[option.code]) {
        	regCode=this.reg[option.code];
            var reg = new RegExp(regCode);
            if (!reg.test(fvalue)) {
            	this.message(option,option.message);
                return false;
            }else{
            	option.obj.parent().find("p").remove();
            }
        }else{
        	try{  
        		var reg = new RegExp(option.code, "gi");
                if (!reg.test(fvalue)) {
                    this.message(option,option.message);
                    return false;
                }else{
                	option.obj.parent().find("p").remove();
                }
    		}catch(e){  
    			this.message(option,"正则表达式'"+(option.code||"")+"'无效.");
    			return false;
    		}  
        }
        return true;
    },
    // 功能描述：提示消息
    message: function (option,message) {
    	var obj=option.obj.parent();
    	if(obj.has("p").length >0){
    		obj.closest(".form-group").removeClass("has-success").addClass("has-error");
    		obj.find("p:eq(0)").html('<i class=\"fa fa-times-circle\"></i>  '+message);
    	}else{
    		obj.closest(".form-group").removeClass("has-success").addClass("has-error");
    		obj.append("<p class=\"help-block m-b-none\"><i class=\"fa fa-times-circle\"></i>  "+message+"</p>");
    	}
    	option.obj.focus();
    }
}

jQuery.check = function (option) {
    var result = new check(option).result;
    return result === false ? false : true;
}

jQuery.checkall = function (all) {
    all = all === false ? false : true;
    var success = true;
    jQuery("input[type=text],input[type=password],textarea").each(function () {
        var check = jQuery(this).data("check") || "";
        if (check == "") return;
        var option = {};
        try {
            var temp = check.split(/;\s?/);
            if (!jQuery.isArray(temp) || temp.length == 0) return;
            var item = null;
            for (var i = 0; i < temp.length; i++) {
                if (temp[i].indexOf(":") < 0) continue;
                item = temp[i].split(/\s?:\s?/);
                if (!jQuery.isArray(item) || item.length == 0) continue;
                option.obj = jQuery(this);
                option[item[0]] = item[1];
            }
        } catch (e) {
            return;
        }
        if (all === true) {
            option.check = true;
            option.fvalue = jQuery(this).val() || "";
            if (option.require == "true" && option.fvalue == "") {
            	var obj=option.obj.parent();
            	if(obj.has("p").length >0){
            		obj.find("p:eq(0)").html('<i class=\"fa fa-times-circle\"></i>  '+option.message);
            	}else{
            		obj.closest(".form-group").removeClass("has-error").addClass("has-success");
            		obj.append("<p class=\"help-block m-b-none\"><i class=\"fa fa-times-circle\"></i>  "+option.message+"</p>");
            	}
            	option.obj.focus();
                success = false;
                return false;
            }
            if (!jQuery.check(option)) {
                success = false;
                return false;
            }
        } else {
            jQuery.check(option);
        }
    });
    return success;
}

jQuery.dataFormater=function(){
	 jQuery("[data-type=enum]").each(function () {
		var text = jQuery(this).text();
		if(text==undefined || text==''){
			jQuery(this).text("");
			return;
		}
	    var opts =jQuery(this).data("opt");
		for(var key in opts){
			if(text==key){
				jQuery(this).text(opts[key]);
				return;
			}
		}
		jQuery(this).text(opts[key])
	 });
	 
	 jQuery("[data-type=datetime]").each(function () {
		var formater= jQuery(this).data('formater');
		if(formater==undefined || formater==''){
			formater="yyyy-MM-dd HH:mm:ss";
		}
		var text = jQuery(this).text();
	    if(text==undefined || text==''){
			jQuery(this).text("");
			return;
		}
		jQuery(this).text(new Date(Number(text)).format(formater))
	 });
	 
	 jQuery("[data-type=date]").each(function () {
		var formater= jQuery(this).data('formater');
		if(formater==undefined || formater==''){
			formater="yyyy-MM-dd";
		}
		var data = jQuery(this).text();
	    if(data==undefined || data==''){
	    	jQuery(this).text("");
	    	return;
		}
	    jQuery(this).text(new Date(Number(text)).format(formater))
	 });
	
	 jQuery("[data-type=substr]").each(function () {
		var data = jQuery(this).text();
		var range=jQuery(this).data('range');
		if(range==undefined || range==''){
			range="1,2";
		}
		var start=Number(range.substr(0,1));
		var end=Number(range.substr(2,3));
		if(start==undefined || start==''){
			start=1;
		}
		if(end==undefined || end==''){
			end=100;
		}
		if(data==undefined || data==''){
			jQuery(this).text("");
			return;
		}
		var size_=data.length;
		if(size_<end){
			jQuery(this).text(data.substr(start,end));
			return;
		}
		jQuery(this).text(data.substr(start,end)+"...")
	});
}

var deleteAjax=function(me,callback){
	$.confirm({
	    text: $(me).data("title")||"确定删除这条数据么?",
	    confirm: function() {
	    	$.ajax({  
				url:$(me).data("url"),  
				data:{
					id:$(me).data("id"),	
				},   
				type: 'POST',
				dataType:'json',  
				success:function(response){
					if(response.code==0){
						$("#"+$(me).parent().parent().parent().parent().attr("id")+"_search").click();
						if (typeof callback === 'undefined') {
						}else{
							callback(response);
						}
						return true;
					}else{
						alert(response.message);
/*
                        $.confirm({cancelButton: "关   闭",title: '提示',text: (me).data("title")+"错误!"||"删除数据错误!"});
*/
					}
					return false;
				}  
		    }); 
	    },
	    cancel: function() {
	        return true;
	    }
	});
}

var deleteAjaxIsOffer=function(me,callback){
	$.confirm({
	    text: $(me).data("title")||"确定删除这条数据么?",
	    confirm: function() {
	    	$.ajax({  
				url:$(me).data("url"),  
				data:{
					id:$(me).data("did"),	
					isoffer:$(me).data("dname"),
					serialno:$(me).data("serialno"),
				},   
				type: 'POST',
				dataType:'json',  
				success:function(response){
					if(response.code==0){
						$("#"+$(me).parent().parent().parent().parent().attr("id")+"_search").click();
						if (typeof callback === 'undefined') {
						}else{
							callback(response);
						}
						return true;
					}else{
					    $.confirm({cancelButton: "关   闭",title: '提示',text: (me).data("title")+"错误!"||"删除数据错误!"});
					}
					return false;
				}  
				
		    }); 
	    },
	    cancel: function() {
	        return true;
	    }
	});
}

jQuery.searchFormInit=function(){
    var startTime = {
        elem: "#startTime",
        format: "YYYY/MM/DD hh:mm:ss",
        min: "2001-06-16 23:59:59",
        max:laydate.now(10),
        istime: true,
        istoday: true,
        choose: function(datas) {
        	endTime.min = datas;
        	endTime.start = datas
        }
    };
    var endTime = {
        elem: "#endTime",
        format: "YYYY/MM/DD hh:mm:ss",
        min: "2001-06-16 23:59:59",
        max: "2099-06-16 23:59:59",
        istime: true,
        istoday: false,
        choose: function(datas) {
        	startTime.max = datas
        }
    };
    laydate(startTime);
    laydate(endTime);
    
    $(".chosen-select").chosen({ width: '100%' });
}

Date.prototype.format = function(format) {  
    var str = format;   
    var Week = ['日','一','二','三','四','五','六'];  
    str=str.replace(/yyyy|YYYY/,this.getFullYear());   
    str=str.replace(/yy|YY/,(this.getYear() % 100)>9?(this.getYear() % 100).toString():'0' + (this.getYear() % 100));   
    str=str.replace(/MM/,(this.getMonth()+ 1)>9?(this.getMonth()+ 1).toString():'0' + (this.getMonth()+1));   
    str=str.replace(/M/g,(this.getMonth()+1));   
    str=str.replace(/w|W/g,Week[this.getDay()]);   
    str=str.replace(/dd|DD/,this.getDate()>9?this.getDate().toString():'0' + this.getDate());   
    str=str.replace(/d|D/g,this.getDate());   
    str=str.replace(/hh|HH/,this.getHours()>9?this.getHours().toString():'0' + this.getHours());   
    str=str.replace(/h|H/g,this.getHours());   
    str=str.replace(/mm/,this.getMinutes()>9?this.getMinutes().toString():'0' + this.getMinutes());   
    str=str.replace(/m/g,this.getMinutes());   
    str=str.replace(/ss|SS/,this.getSeconds()>9?this.getSeconds().toString():'0' + this.getSeconds());   
    str=str.replace(/s|S/g,this.getSeconds());   
    return str;   
}   
	  
jQuery.browerCheck=function(){
	if (navigator.userAgent.indexOf("MSIE") >= 0 &&(jQuery.browser.version == "6.0"||jQuery.browser.version == "7.0" || jQuery.browser.version == "8.0")) {
		if($("#unsupported-browser").length<=0){
    	 	var meContent=[];
			meContent.push('<div id="unsupported-browser" class="clearfix" style="text-align:center">');
			meContent.push('<H5>请注意，系统不再支持IE 6.7.8版本浏览器.</H5>');
			meContent.push('<P>我们建议升级到最新的 <A href="http://windows.microsoft.com/ie">Internet Explorer</A>, <A href="https://chrome.google.com">Google Chrome</A>,或者 <A href="https://mozilla.org/firefox/">Firefox</A>.</P>');
			meContent.push('<P>如果你开启IE兼容性， <A href="http://windows.microsoft.com/en-US/windows7/webpages-look-incorrect-in-Internet-Explorer">请确保关掉使用。</A>.</P></div>');
			swal({
                title: "警告",
                text: meContent.join("")
            })
		}
     } 
}

jQuery.isNotEmpty=function(me){
	if(me!=undefined && me !="undefined" && me !=null && me !=''){
		 return true;
	}
	return false;
}

jQuery.serializeForm = function(formId) {
	var o = {};
	var form="#"+formId;
	if(formId==undefined || formId==''){
		form=".form-horizontal"
	}
	$(form).find("input").each(function() {
		var name=$(this).attr("name");
		var value=$(this).attr("value");
		if (o[name]) {
			if (!o[name].push) {
				o[name] = [ o[name] ];
			}
			o[name].push(value|| '');
		} else {
			o[name] = value || '';
		}
	});
	return o;
};

$(function(){
	handlerWindow($(window));
})

var handlerWindow=function(win){
    var width=win.width();
    var height=win.height();
    var hasShow=true;
    if (height >= 820) {
    	hasShow=true;
	}else {
		hasShow=false;
    }
    if (width >= 820) {
    	hasShow=true;
	}else {
		hasShow=false;
    }
    if(hasShow){
    	$(".ibox-content > form").removeClass("hiden");
    }else{
    	$(".ibox-content > form").addClass("hiden");
    }
}

$(window).on('resize', function(){
	handlerWindow($(this));
});

/*!
 * jquery.confirm
 */
(function ($) {
	
	$.fn.mySerializeForm = function() {
	    // don't do anything if we didn't get any elements
	    if ( this.length < 1) { 
	      return false; 
	    }
	    var data = {};
	    var lookup = data; //current reference of data
	    var selector = ':input[type!="checkbox"][type!="radio"][data-disabled!="true"], input:checked';
	    var parse = function() {
	      // Ignore disabled elements
	      if (this.disabled) {
	        return;
	      }
	      // data[a][b] becomes [ data, a, b ]
	      var named = this.name.replace(/\[([^\]]+)?\]/g, ',$1').split(',');
	      var cap = named.length - 1;
	      var $el = $( this );
	      // Ensure that only elements with valid `name` properties will be serialized
	      if ( named[ 0 ] ) {
	        for ( var i = 0; i < cap; i++ ) {
	          lookup = lookup[ named[i] ] = lookup[ named[i] ] ||
	            ( (named[ i + 1 ] === "" || named[ i + 1 ] === '0') ? [] : {} );
	        }
	        // at the end, push or assign the value
	        if ( lookup.length !==  undefined ) {
	          //lookup.push( $el.val() );
	        }else {
	          lookup[ named[ cap ] ]  = $el.val();
	        }
	        lookup = data;
	      }
	    };
	    // first, check for elements passed into this function
	    this.filter( selector ).each( parse );
	    // then parse possible child elements
	    this.find( selector ).each( parse );
	    // return data
	    return data;
	  };
	
	
    $.fn.confirm = function (options) {
        if (typeof options === 'undefined') {
            options = {};
        }
        this.click(function (e) {
            e.preventDefault();
            var newOptions = $.extend({
                button: $(this)
            }, options);
            $.confirm(newOptions, e);
        });
        return this;
    };

    /**
     * Show a confirmation dialog
     * @param [options] {{title, text, confirm, cancel, confirmButton, cancelButton, post, submitForm, confirmButtonClass}}
     * @param [e] {Event}
     */
    $.confirm = function (options, e) {
        // Do nothing when active confirm modal.
        if ($('.confirmation-modal').length > 0)
            return;
        // Parse options defined with "data-" attributes
        var dataOptions = {};
        if (options.button) {
            var dataOptionsMapping = {
                'title': 'title',
                'text': 'text',
                'confirm-button': 'confirmButton',
                'submit-form': 'submitForm',
                'cancel-button': 'cancelButton',
                'confirm-button-class': 'confirmButtonClass',
                'cancel-button-class': 'cancelButtonClass',
                'dialog-class': 'dialogClass'
            };
            $.each(dataOptionsMapping, function(attributeName, optionName) {
                var value = options.button.data(attributeName);
                if (value) {
                    dataOptions[optionName] = value;
                }
            });
        }

        // Default options
        var settings = $.extend({}, $.confirm.options, {
            confirm: function () {
                if (dataOptions.submitForm){
                    e.target.closest("form").submit();
                } else {
                    var url = e && (('string' === typeof e && e) || (e.currentTarget && e.currentTarget.attributes['href'].value));
                    if (url) {
                        if (options.post) {
                            var form = $('<form method="post" class="hide" action="' + url + '"></form>');
                            $("body").append(form);
                            form.submit();
                        } else {
                            window.location = url;
                        }
                    }
                }
            },
            cancel: function (o) {
            },
            button: null
        }, dataOptions, options);

        // Modal
        var modalHeader = '';
        if (settings.title !== '') {
            modalHeader =
                '<div class="modal-header">' +
                    '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
                    '<h4 class="modal-title" style="text-align:center; vertical-align:middle;">' + settings.title+'</h4>' +
                '</div>';
        }
        var modalHTML =[];
        modalHTML.push('<div class="confirmation-modal modal fade" tabindex="-1" role="dialog">');
        modalHTML.push('<div class="'+ settings.dialogClass +'">' );
        modalHTML.push('<div class="modal-content">' );
        modalHTML.push(modalHeader );
        modalHTML.push('<div class="modal-body" style="text-align:center; vertical-align:middle;">' + settings.text + '</div>' );
        if((settings.confirmButton !=false) || (settings.cancelButton !=false)){
        	 modalHTML.push('<div class="modal-footer">' );
        }
        if(settings.confirmButton!=false){
        	 modalHTML.push('<button class="confirm btn btn-default" type="button" data-dismiss="modal">' +settings.confirmButton +'</button>' );
        }
        if(settings.cancelButton!=false){
        	 modalHTML.push('<button class="cancel btn btn-default" type="button" data-dismiss="modal">' +settings.cancelButton +'</button>' );
        }
        if(( settings.confirmButton!=false) || ( settings.cancelButton!=false)){
        	modalHTML.push('</div>');
        }
        modalHTML.push('</div>' );
        modalHTML.push('</div>' );
        modalHTML.push('</div>');

        var modal = $(modalHTML.join(""));

        modal.on('shown.bs.modal', function () {
            modal.find(".btn-primary:first").focus();
        });
        modal.on('hidden.bs.modal', function () {
            modal.remove();
        });
        modal.find(".confirm").click(function () {
        	return settings.confirm(settings.button);
        });
        modal.find(".cancel").click(function () {
            return settings.cancel(settings.button);
        });
        // Show the modal
        $("body").append(modal);
        modal.modal('show');
    };

    /**
     * Globally definable rules
     */
    $.confirm.options = {
        text: "Are you sure?",
        title: "",
        confirmButton:"确定",
        cancelButton: "取消",
        post: false,
        submitForm: false,
        confirmButtonClass: "btn-default",
        cancelButtonClass: "btn-default",
        dialogClass: "modal-dialog"
    }
})(jQuery);
/*
$.confirm({
    text: "Are you sure you want to delete that comment?",
    confirm: function() {
        delete();
    },
    cancel: function() {
        // nothing to do
    }
});
<button class="confirm" type="button">Delete the comment</button>
$(".confirm").confirm({
    text: "Are you sure you want to delete that comment?",
    title: "Confirmation required",
    confirm: function(button) {
        delete();
    },
    cancel: function(button) {
        // nothing to do
    },
    confirmButton: "Yes I am",
    cancelButton: "No",
    post: true,
    confirmButtonClass: "btn-danger",
    cancelButtonClass: "btn-default",
    dialogClass: "modal-dialog modal-lg" // Bootstrap classes for large modal
});
*/
jQuery.notify=function(title,text){
	$.confirm({
		text: text,
	    title: title,
	    post: false,
	    submitForm: false,
	    confirmButton:false,
	    cancelButton:false,
	    dialogClass: "modal-dialog modal-sm"
    });
}

/**
 * 全局ajax处理
 */
jQuery.ajaxSetup({
	timeout:5000,
	cache:false,
	beforeSend: function(xhr) {
        xhr.setRequestHeader('token', cookie("token"));
    },error: function (XMLHttpRequest, textStatus, errorThrown){
        if(XMLHttpRequest.status==403){
        	jQuery.notify("提示","您没有权限访问此资源或进行此操作" );
            return false;
        }else  if(XMLHttpRequest.status==500){
        	jQuery.notify("提示","服务器端出现错误.请联系管理员!" );
            return false;
        } else  if(XMLHttpRequest.status==404){
        	jQuery.notify("提示", "你访问的资源不存在.请联系管理员!");
            return false;
        }
    },success:function(response){
    	if(response!=null){
    		var code=response.code || 0;
    		if(code==0){//success
    		}else{
    			jQuery.notify("提示","本次响应不正确["+response.message|''+"], 请重试!");
    		}
    	}
	},complete:function(XMLHttpRequest,textStatus){
        var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头,sessionstatus，
        if(sessionstatus=='timeout'){
        	var pathName = window.location.pathname.substring(1);   
		    var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));   
            window.location="/"+webName+"/login"; //跳转到登陆页面
        }
    }
});

