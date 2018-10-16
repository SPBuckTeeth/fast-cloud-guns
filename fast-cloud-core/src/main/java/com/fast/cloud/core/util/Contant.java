package com.fast.cloud.core.util;

import java.util.ArrayList;
import java.util.List;

public class Contant {
    public static String commonStringUtil(String str) {
        String ss = "";
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if(charArray[i] >= 'A' && charArray[i] <= 'Z'){
                ss += "_"+charArray[i];
            } else {
                ss += charArray[i];
            }
        }
        return ss.toLowerCase();
    }

    /**
     * 登陆名键
     */
    public static final  String  LOGIN_USER="loginUser";

    public enum MYBATIS_SPECIAL_STRING{
        ORDER_BY,LIMIT,COLUMNS,TABLES,WHERE,LIKE;
        public static List<String> list(){
            List<String> result = new ArrayList<String>();
            for (MYBATIS_SPECIAL_STRING entry : MYBATIS_SPECIAL_STRING.values()) {
                result.add(entry.name());
            }
            return result;
        }
    }



    public static void main(String[] args) {
        System.out.println(MYBATIS_SPECIAL_STRING.LIMIT.name());
    }


    //app消息推送 极光推送使用
    public static final String appKey ="9347818def5eaeeac169419d";
    public static final String masterSecret = "70abab1e5d86affde0ad39e0";

    /**
     * GoEasy消息推送
     */
    public static final String GOEASY_REGIONHOST = "http://rest-hangzhou.goeasy.io";
    public static final String GOEASY_APPKEY = "BC-85b4c3267404457b8fdbea1909956420";
    public static final String GOEASY_CHANNEL = "fastInfoChannel";

    /**
     * redis过期时间
     */
    public class REDIS_EXPIREtIME {
        /** 7天过期 7*24*60*60 **/
        public static final int SEVENDAYS = 604800;
    }

    /**
     * 订单进度
     * @author Administrator
     *
     */
    public class ORDER_PROGRESS{
        /** 被拒绝 **/
        public static final long REJECTED = -100L;
        /** 待揽件 **/
        public static final long WAIT_PACKAGE = 100L;
        /** 已揽件 **/
        public static final long PACKAGED = 200L;
        /** 已入仓 **/
        public static final long STORED = 300L;
        /** 待付款 **/
        public static final long WAIT_PAY = 350L;
        /** 已发出 **/
        public static final long SET_OUT = 400L;
        /** 运输中 **/
        public static final long TRANSPORT = 500L;
        /** 清关中 **/
        public static final long CLEARANCE = 600L;
        /** 派送中 **/
        public static final long DISPATCH = 700L;
        /** 已签收 **/
        public static final long SIGN_IN = 800L;
    }

    /**
     * 服务商类型
     * @author Administrator
     *
     */
    public class SERVICE_PLATFORM{
        /** UPS **/
        public static final long UPS = 10L;
    }

    /**
     * 消息模板
     */
    public class MESSAGE_TEMPLATE{
        /** 订单流程推送 **/
        public static final String ORDER_PROGRESS = "ORDER_PROGRESS";
    }

    public static String DEFAULT_ADDRESS_NAME = "defaultDeliveryName";

    public static String DEFAULT_ADDRESS_PHONE = "defaultDeliveryPhone";

    public static String DEFAULT_ADDRESS = "defaultDeliveryAddress";

    /**
     * 数字
     */
    public class BasicNum {
        public static final String ZERO_S = "0";
        public static final String ONE_S = "1";
        public static final String TWO_S = "2";
        public static final String THREE_S = "3";
        public static final String FOUR_S = "4";
        public static final String FIVE_S = "5";

        public static final int ZERO = 0;
        public static final int ONE = 1;
        public static final int TWO = 2;
        public static final int THREE = 3;
        public static final int FOUR = 4;

        public static final int SIXTY = 60;
    }

    public final static String FORMID = "formId";
}
