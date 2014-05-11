package com.jason.usedcar.util;

public class HttpUtil {
    public static final String URI = "http://www.2soce.com:8080/";

    public static final String SIGINON_URI = URI.concat("signon.json");

    /**
     * 经销商注册地址
     */
    public static final String DEALER_REGISTER_URI = URI.concat("reselleSignon.json");

    /**
     * 登录地址
     */
    public static final String LOGIN_URI = URI.concat("login.json");

    /**
     * 获取验证码地址
     */
    public static final String OBTAIN_CODE_URI = URI.concat("account/obtainCode");
}
