package com.omaru.wedding.util;

import java.security.SecureRandom;
import java.util.Base64;

//Token作成クラス
public class TokenGenerator {

    private static final SecureRandom  secureRandom = new SecureRandom();

    public static String generate() {
        byte[] randomBytes = new byte[32]; // 256bit
        secureRandom.nextBytes(randomBytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
