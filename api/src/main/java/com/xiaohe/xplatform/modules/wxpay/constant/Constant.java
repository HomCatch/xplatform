package com.xiaohe.xplatform.modules.wxpay.constant;

/**
 * Created by Hyman on 2017/2/27.
 */
public class Constant {

    public static final String DOMAIN = "http://localhost";
    /**
     * 小程序appId
     */
    public static final String APP_ID = "wx7a27365cced50f99";
    /**
     * 小程序 APP_SECRET
     */
    public static final String APP_SECRET = "d9f3746a1f2f6388ec65a3f36617ff21";

    /**
     * 商户号
     */
    public static final String MCH_ID = "1420120502";
    /**
     * 商户key
     */
    public static final String MCH_KEY = "Hg8njDc26qxHzzATXT1la7k48oc2L6B9";

    public static final String URL_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 支付回调地址
     */
    public static final String URL_NOTIFY = "https://iotsvr.he-live.com/sellWater/wechatNotify";

    public static final String TIME_FORMAT = "yyyyMMddHHmmss";
    /**
     * 单位是day
     */
    public static final int TIME_EXPIRE = 2;

}
