package com.jason.usedcar.model.param;

import lombok.Data;

@Data
public class BindNewPhoneParam extends Param {

    private String phoneNumber;

    private String code;
}
