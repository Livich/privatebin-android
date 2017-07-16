package com.livich.privatebin.sys;

import java.security.SecureRandom;
import java.util.Random;

public class PasswordGenerator {
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public static byte[] randomBytes(int len) {
        byte[] b = new byte[len];
        new Random().nextBytes(b);
        return b;
    }
}
