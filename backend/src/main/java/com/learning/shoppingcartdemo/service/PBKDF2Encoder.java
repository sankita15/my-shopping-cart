package com.learning.shoppingcartdemo.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.util.Base64;

import static org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512;

@Component
public class PBKDF2Encoder implements PasswordEncoder {

    @Value("${app.security.password.encoder.secret}")
    private String secret;

    private static final Integer ITERATION = 33;
    private static final Integer KEY_LENGTH = 256;

    @Override
    @SneakyThrows
    public String encode(CharSequence cs) {
        byte[] encodedSecret = SecretKeyFactory.getInstance(PBKDF2WithHmacSHA512.name())
            .generateSecret(new PBEKeySpec(cs.toString().toCharArray(), secret.getBytes(), ITERATION, KEY_LENGTH))
            .getEncoded();

        return Base64.getEncoder().encodeToString(encodedSecret);
    }

    @Override
    public boolean matches(CharSequence cs, String string) {
        return encode(cs).equals(string);
    }
}
