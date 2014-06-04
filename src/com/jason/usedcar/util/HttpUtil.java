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

    /**
     * 上传图片地址
     */
    public static final String IMAGE_UPLOAD_URI = URI.concat("product/imageUpload.json");

    /**
     * 发布二手车地址
     */
    public static final String PUBLISH_USED_CAR_URI = URI.concat("product/publishUsedCar.json");

    /**
     * 查询二手车地址
     */
    public static final String GET_USED_CAR_URI = URI.concat("product/getUsedCar.json");

    /**
     * 删除二手车地址
     */
    public static final String DELETE_USED_CAR_URI = URI.concat("product/deleteusedcar.json");

    /**
     * 修改密码地址
     */
    public static final String UPDATE_PWD_URI = URI.concat("account/updatePassword.json");

    /**
     * 查看用户信息
     */
    public static final String VIEW_USER_INFO_URI = URI.concat("account/viewUserInfo");

    /**
     * 更改用户信息
     */
    public static final String UPDATE_USER_INFO_URI = URI.concat("account/updateUserInfo");
}
