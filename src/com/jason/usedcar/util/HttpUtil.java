package com.jason.usedcar.util;

public class HttpUtil {
    public static final String URI = "http://www.2soce.com:8080/";

    public static final String SIGINON_URI = URI.concat("signon.json");

    /**
     * ������ע���ַ
     */
    public static final String DEALER_REGISTER_URI = URI.concat("reselleSignon.json");

    /**
     * ��¼��ַ
     */
    public static final String LOGIN_URI = URI.concat("login.json");

    /**
     * ��ȡ��֤���ַ
     */
    public static final String OBTAIN_CODE_URI = URI.concat("account/obtainCode");
}
