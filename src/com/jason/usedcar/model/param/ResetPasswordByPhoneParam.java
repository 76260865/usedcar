package com.jason.usedcar.model.param;

import lombok.Data;

@Data
public class ResetPasswordByPhoneParam extends Param {

    private String principle;

    private String activeCode;

    private String newPassword;

    private String confirmPassword;
}
