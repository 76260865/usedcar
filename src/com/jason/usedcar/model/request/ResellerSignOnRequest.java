package com.jason.usedcar.model.request;

import lombok.Data;

@Data
public class ResellerSignOnRequest extends SignOnRequest {

    private String resellerName;

    private int resellerType;

    private String adress;
}
