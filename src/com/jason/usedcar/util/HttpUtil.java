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

    /**
     * �ϴ�ͼƬ��ַ
     */
    public static final String IMAGE_UPLOAD_URI = URI.concat("product/imageUpload.json");

    /**
     * �������ֳ���ַ
     */
    public static final String PUBLISH_USED_CAR_URI = URI.concat("product/publishUsedCar.json");

    /**
     * ��ѯ���ֳ���ַ
     */
    public static final String GET_USED_CAR_URI = URI.concat("product/getUsedCar.json");

    /**
     * ɾ�����ֳ���ַ
     */
    public static final String DELETE_USED_CAR_URI = URI.concat("product/deleteusedcar.json");

    /**
     * �޸������ַ
     */
    public static final String UPDATE_PWD_URI = URI.concat("account/updatePassword.json");

    /**
     * �鿴�û���Ϣ
     */
    public static final String VIEW_USER_INFO_URI = URI.concat("account/viewUserInfo");

    /**
     * �����û���Ϣ
     */
    public static final String UPDATE_USER_INFO_URI = URI.concat("account/updateUserInfo");
}
