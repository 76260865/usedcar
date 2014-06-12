package com.jason.usedcar.model.request;

import lombok.Data;

@Data
public class ForgetPasswordRequest extends Request {

    private String loginName;
}
