package com.openvehicletracking.restapi.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;

public class SHA1PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] b = messageDigest.digest(rawPassword.toString().getBytes("UTF-8"));

            StringBuilder result = new StringBuilder();
            for (byte aB : b) {
                result.append(Integer.toString((aB & 0xff) + 0x100, 16).substring(1));
            }

            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
