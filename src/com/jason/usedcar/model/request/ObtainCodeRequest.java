package com.jason.usedcar.model.request;

import lombok.Data;

@Data
public class ObtainCodeRequest extends Request {

    private String phoneNumber;

    private String type;
}
