package com.fast.cloud.core.util;

public enum ErrorCode {

	SUCCESS(0, "Success"),//成功

	ERROR(1, "Error"),//错误
	
	OBJECT_EXITS(2, "Object exits!"),// 对象退出

	OBJECT_NOT_EXITS(3, "Object not exits!"),//对象不存在

	USER_PASSWORD_INVALID(4, "Password invalid!"),//密码无效

	USER_NAME_INVALID(5, "Username invalid!"),//用户名无效

	PARAMETER_INVALID(6, "Parameter invalid"),//属性无效

	EMAIL_INVALID(7, "Email invalid!"),//邮箱无效

	USER_NAME_EXITS(8, "Username exits!"),

	USER_PASSWORD_EXITS(9, "Password exits!"),

	USER_EMAIL_EXITS(10, "Email exits!"),

	USER_PASSWORD_NOT_EQUALS(11,
			"The user password for the input is different!"),//输入的用户密码不同

	USER_NAME_NOT_EQUALS_PASSWORD(12,
			"Username and password can not be the equals!"),//用户名和密码不能相等

	USER_AGREEMENT(13, "You don't agree with the user agreement!"),//你不同意用户协议

	FORM_REPEAT_SUBMIT(14, "Repeat the form!"),//重复的形式

	CSRF_TOKEN_INVALID(15, "Data connection errors!"),//数据连接错误

	DB_CONNECTION_ERROR(16, "Data connection errors!"),//数据连接错误

	USER_NOT_LOGINED(17, "The user is not logined！"),//用户不登录

	NOT_APP_CLIENT(18, "The is not app client request！"),//不是应用程序客户端请求

	NOT_ADMIN_CLIENT(19, "The is not admin client request！"),//不是admin客户机请求

	INTERNAL_ERROR(20, "Internal error!"),//内部错误

	FILE_NOT_FOUNT(21, "File not fount！"),//文件未发现

	FILE_SIZE_LIMIT_EXCEEDED(22, "File Size Limit Exceeded！"),//超出文件大小限制

	INVALID_EXTENSION(23, "Invalid Extension！"),//无效的扩展

	FILE_NAME_LENGTH_LIMIT_EXCEEDED(24, "fileName Length Limit Exceeded！"),//超过文件名长度限制

	USER_NOT_PERMISSION(25, "User is not permission！"),//用户没有权限
	
	ID_CARD_ILLEAGAL(26, "ID card is illegal！"),//身份证是非法的

	OPREATION_BEYOND_RANFE(27, "Operation beyond range！"),//操作超出范围

	NOT_CASCADE_DELETE(28, "Can not cascade delete！"),//不能级联删除

	INVALID_REQUEST(29, "Invalid request！"),//无效的请求
	
	USER_NOT_LOGINED2(30, "用户已经被禁用!"),
	
	SEND_CODE_FAILE(31, "发送验证码失败!"),
	
	ATTRIBUTE_MISSSING(32,"Attribute missing"),//属性缺少

	UNKNOWN(2000, "Unknown error!"),//未知错误

	INVALID_ACCESS(10000, "I'm sorry, you don't have the access");//对不起，你没有这个通道

	private int code;
	private String message;

	private ErrorCode(int code, String message) {
		this.message = message;
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
