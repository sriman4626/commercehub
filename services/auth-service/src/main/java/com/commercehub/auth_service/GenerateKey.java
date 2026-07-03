package com.commercehub.auth_service;

import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

public class GenerateKey {

    public static void main(String[] args) {

        String key = Encoders.BASE64.encode(
                Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256)
                        .getEncoded());

        System.out.println(key);
    }

}
