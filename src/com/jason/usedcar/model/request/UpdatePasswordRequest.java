package com.jason.usedcar.model.request;

import lombok.Data;

@Data
public class UpdatePasswordRequest extends Request {

    private String oldPWD;

    private String newPWD;

    private String confirmPWD;
}
