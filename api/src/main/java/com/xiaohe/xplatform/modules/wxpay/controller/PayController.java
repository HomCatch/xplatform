package com.xiaohe.xplatform.modules.wxpay.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xiaohe.xplatform.modules.wxpay.constant.Constant;
import com.xiaohe.xplatform.modules.wxpay.entity.WechatUnifiedOrder;
import com.xiaohe.xplatform.modules.wxpay.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.xiaohe.xplatform.modules.wxpay.constant.Constant.APP_ID;

/**
 * @author hzh
 */
@RestController
public class PayController {

    private static Logger log = Logger.getLogger(PayController.class);

    @RequestMapping(value = "notify")
    public void notify(HttpServletRequest request) {
        System.out.println("aaaaaa");
    }

    @ResponseBody
    @RequestMapping(value = "/prepay", produces = "text/html;charset=UTF-8")
    public String prePay(String code, ModelMap model, HttpServletRequest request) {

        System.out.println("code:" + code);
        String content = null;
        Map<String, java.io.Serializable> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        boolean result = true;
        String info = "";

        log.info("\n======================================================");
        log.info("code: " + code);

        String openId = getOpenId(code);
        System.out.println("获取openid啊" + openId);
        if (StringUtils.isBlank(openId)) {
            result = false;
            info = "获取到openId为空";
        } else {
            openId = openId.replace("\"", "").trim();

            String clientIP = CommonUtil.getClientIp(request);

            log.info("openId: " + openId + ", clientIP: " + clientIP);
            System.out.println("openId: " + openId + ", clientIP: " + clientIP);
            String randomNonceStr = RandomUtils.generateMixString(32);
            String prepayId = unifiedOrder(openId, clientIP, randomNonceStr);

            log.info("prepayId: " + prepayId);
            System.out.println(prepayId);
            if (StringUtils.isBlank(prepayId)) {
                result = false;
                info = "出错了，未获取到prepayId";
            } else {
                map.put("prepayId", prepayId);
                map.put("nonceStr", randomNonceStr);
            }
        }

        try {
            map.put("result", result);
            map.put("info", info);
            content = mapper.writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }


    private String getOpenId(String code) {
//        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + Constant.APP_ID + "&secret=" + Constant.APP_SECRET + "&js_code=" + code + "&grant_type=authorization_code";
        /**
         * 获取用户openId的url
         */
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + Constant.APP_ID + "&secret=" + Constant.APP_SECRET + "&js_code=" + code + "&grant_type=authorization_code";

        HttpUtil httpUtil = new HttpUtil();
        try {

            HttpResult httpResult = httpUtil.doGet(url, null, null);

            if (httpResult.getStatusCode() == 200) {

                JsonParser jsonParser = new JsonParser();
                JsonObject obj = (JsonObject) jsonParser.parse(httpResult.getBody());

                log.info("getOpenId: " + obj.toString());
                System.out.println(obj.toString());
                if (obj.get("errcode") != null) {
                    log.info("getOpenId returns errcode: " + obj.get("errcode"));
                    return "";
                } else {
                    return obj.get("openid").toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 调用统一下单接口
     *
     * @param openId         用户id
     * @param clientIP       客服端id
     * @param randomNonceStr 随机数组
     * @return 订单编号
     */
    private String unifiedOrder(String openId, String clientIP, String randomNonceStr) {

        try {

            String url = Constant.URL_UNIFIED_ORDER;

            WechatUnifiedOrder wechatUnifiedOrder = createWechatUnifiedOrder(openId, clientIP, randomNonceStr);
            wechatUnifiedOrder.setSign(SignUtil.sign(SignUtil.bean2TreeMap(wechatUnifiedOrder)).toUpperCase());
            ;
            String xml = XmlUtil.beanToXml(new ByteArrayOutputStream(), wechatUnifiedOrder);
            String requestXml = xml != null ? xml.substring(55) : "";
            System.out.println("requestXml:" + requestXml);
            StringBuffer buffer = HttpUtil.httpsRequest(url, "POST", requestXml);
            log.info("unifiedOrder request return body: \n" + buffer.toString());
            Map<String, String> result = CommonUtil.parseXml(buffer.toString());


            String returnCode = result.get("return_code");
            if (StringUtils.isNotBlank(returnCode) && "SUCCESS".equals(returnCode)) {

                String returnMsg = result.get("return_msg");
                if (StringUtils.isNotBlank(returnMsg) && !"OK".equals(returnMsg)) {

                    return "";
                }

                String prepayId = result.get("prepay_id");

                return StringUtils.isBlank(prepayId) ? "" : prepayId;

            } else {
                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    private WechatUnifiedOrder createWechatUnifiedOrder(String openId, String clientIP, String randomNonceStr) {

        Date date = new Date();
        String timeStart = TimeUtils.getFormatTime(date, Constant.TIME_FORMAT);
        String timeExpire = TimeUtils.getFormatTime(TimeUtils.addDay(date, Constant.TIME_EXPIRE), Constant.TIME_FORMAT);

        String randomOrderId = CommonUtil.getRandomOrderId();

        WechatUnifiedOrder payInfo = new WechatUnifiedOrder();
        payInfo.setAppid(APP_ID);
        payInfo.setMch_id(Constant.MCH_ID);
        payInfo.setDevice_info("WEB");
        payInfo.setNonce_str(randomNonceStr);
        payInfo.setBody("JSAPI支付测试");
        payInfo.setAttach("支付测试");
        payInfo.setOut_trade_no(randomOrderId);
        payInfo.setTotal_fee(1);
        payInfo.setSpbill_create_ip("127.0.0.1");
        payInfo.setTime_start(timeStart);
        payInfo.setTime_expire(timeExpire);
        payInfo.setNotify_url(Constant.URL_NOTIFY);
        payInfo.setTrade_type("JSAPI");
        payInfo.setLimit_pay("no_credit");
        payInfo.setOpenid(openId);

        return payInfo;
    }


}


