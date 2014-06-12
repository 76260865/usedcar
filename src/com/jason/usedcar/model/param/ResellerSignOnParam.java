package com.jason.usedcar.model.param;

import lombok.Data;

@Data
public class ResellerSignOnParam extends SignOnParam {

    private String resellerName;

    private int resellerType;

    private String adress;
}
