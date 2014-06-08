package com.jason.usedcar.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author t77yq @2014.06.08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ObtainCodeResult extends Result {

    private String code;
}
