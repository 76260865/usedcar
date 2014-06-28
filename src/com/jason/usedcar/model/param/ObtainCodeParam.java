package com.jason.usedcar.model.param;

import lombok.Data;

@Data
public class ObtainCodeParam extends Param {

    private String phoneNumber;

    private int type;
}
