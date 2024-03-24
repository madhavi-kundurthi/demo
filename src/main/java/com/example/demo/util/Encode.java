package com.example.demo.util;

import java.util.Base64;

public class Encode {

    public String encode(String originalInput){

        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        return encodedString;
    }
}
