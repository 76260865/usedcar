package com.jason.usedcar.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AcessTokenUtil {
    public static final String mPrivateKey = "uja6snx21b";

    public static String MD5Encode(String str) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(str.getBytes());

        byte[] byteArray = md5.digest();

        return byte2hexString(byteArray);
    }

    public static final String byte2hexString(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buf.toString();
    }
}
