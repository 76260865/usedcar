package com.jason.usedcar.model.param;

import lombok.Data;

@Data
public class ViewUserInfoParam extends Param {

    private String nickname;

    private String realName;

    private String email;

    private boolean bindEmail;

    private boolean sex;

    private String phone;

    private boolean bindPhone;

    private String birthyear;

    private String birthmonth;

    private String birthday;

    private String certificateNumber;

    private String province;

    private String city;

    private String county;

    private String street;
}
