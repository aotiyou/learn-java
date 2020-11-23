package com.zxy.learnjava;

import java.util.Arrays;
import java.util.Base64;

public class Main {

    public static void main(String[] args) {

    }

    public static void base64_urlEncode(){
        byte[] input = new byte[]{ 0x01, 0x02, 0x7f, 0x00 };
        String b64encoded = Base64.getUrlEncoder().encodeToString(input);
        System.out.println(b64encoded);

        byte[] output = Base64.getUrlDecoder().decode(b64encoded);
        System.out.println(Arrays.toString(output));
    }

}


