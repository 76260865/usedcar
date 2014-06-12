package com.jason.usedcar.model.request;

import lombok.Data;

@Data
public class BindNewPhoneRequest extends Request {

    private String phoneNumber;

    private String code;
}
