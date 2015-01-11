package com.jason.usedcar.pm.view;

/**
 * @author t77yq @2014-09-27.
 */
public interface ResellerAuthView extends ViewBase {

    void before();

    void after();

    boolean isReseller();

    void pickOriginBankAddress();
}
