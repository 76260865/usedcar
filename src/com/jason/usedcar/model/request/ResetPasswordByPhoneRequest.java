package com.jason.usedcar.model.request;

import lombok.Data;

@Data
public class ResetPasswordByPhoneRequest extends Request {

    private String principle;

    private String activeCode;

    private String newPassword;

    private String confirmPassword;
}
