package com.jason.usedcar;

/**
 * @author t77yq @2014-11-08.
 */
public final class CacheUtils {

    private static String cancelledOrderId;

    public static String getCancelledOrderId() {
        return cancelledOrderId;
    }

    public static void setCancelledOrderId(String orderId) {
        cancelledOrderId = orderId;
    }

    private static String orderedProductId;

    public static String getOrderedProductId() {
        return orderedProductId;
    }

    public static void setOrderedProductId(String productId) {
        orderedProductId = productId;
    }

    private static CarInfo carInfo;

    public static CarInfo getCarInfo() {
        return carInfo;
    }

    public static void setCarInfo(CarInfo info) {
        carInfo = info;
    }
}
