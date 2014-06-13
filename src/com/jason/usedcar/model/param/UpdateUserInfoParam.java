package com.jason.usedcar.model.param;

import lombok.Data;

@Data
public class UpdateUserInfoParam extends Param {

    private String nickname;

    private String realName;

    private boolean sex;

    private String birthyear;

    private String birthmonth;

    private String birthday;

    private String certificateNumber;

    private String province;

    private String city;

    private String county;

    private String street;
}
