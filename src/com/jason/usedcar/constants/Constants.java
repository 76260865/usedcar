package com.jason.usedcar.constants;

/**
 * @author t77yq @2014.06.08
 */
public final class Constants {

    public static final int PASSWORD_LENGTH_MIN = 6;

    public static final int PASSWORD_LENGTH_MAX = 15;

    public static final class Validator {

        public static final String MSG_PASSWORD_LENGTH = "password too short or long";

        public static final String MSG_PASSWORD_NOT_MATCH = "password not match";
    }

    public static final class ObtainCode {

        public static final int TYPE_REGISTER = 1;

        public static final int TYPE_RESET_PASSWORD = 4;

        public static final int TYPE_BIND_NEW_PHONE = 6;

        public static final int TYPE_VERIFY_ID = 7;
    }

    public static final int REQUEST_LOGIN = 10001;

    public static final class CarDetailsType {

        public static final int BUY = 0;

        public static final int SELL = 1;

        public static final int OTHER = 2;
    }

    public static String URL_BASE = "http://112.124.62.114:80";
}
