package com.jason.usedcar.model.request;

import lombok.Data;

@Data
public class UpdateUserInfoRequest extends Request {

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
