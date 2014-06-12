package com.jason.usedcar.model.request;

import lombok.Data;

@Data
public class GetUsedCarRequest extends Request {

    private String productId;
}
