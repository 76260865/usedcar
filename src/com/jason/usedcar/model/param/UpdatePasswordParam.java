package com.jason.usedcar.model.param;

import lombok.Data;

@Data
public class UpdatePasswordParam extends Param {

    private String oldPWD;

    private String newPWD;

    private String confirmPWD;
}
