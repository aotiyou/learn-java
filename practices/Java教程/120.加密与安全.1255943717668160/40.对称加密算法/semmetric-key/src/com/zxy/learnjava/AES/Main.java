package com.zxy.learnjava.AES;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class Main {

    public static void main(String[] args) throws Exception {
        AESCBCTest();
    }

    public static void AESECBTest() throws Exception {
        // 原文：
        String message = "Hello World";
        // 128位秘钥 = 16 bytes Key：
        byte[] key = "1234567890abcdef".getBytes("UTF-8");
        System.out.println(Arrays.toString(key));
        // 加密：
        byte[] data = message.getBytes("UTF-8");
        byte[] encrypted = encrypt_AES_ECB(key, data);
        System.out.println("Encrypted: " + Base64.getEncoder().encodeToString(encrypted));
        // 解密：
        byte[] decrypted = decrypt_AES_ECB(key, encrypted);
        System.out.println("Decrypted: " + new String(decrypted, "UTF-8"));
    }

    public static void AESCBCTest() throws Exception {
        // 原文：
        String message = "Hello world!";
        System.out.println("Message: " + message);
        // 256为秘钥 = 32 bytes Key：
        byte[] key = "1234567890abcdef1234567890abcdef".getBytes("UTF-8");
        // 加密：
        byte[] data = message.getBytes("UTF-8");
        byte[] encrypted = encrypt_AES_CBC(key, data);
        System.out.println("Encrypted: " + Base64.getEncoder().encodeToString(encrypted));
        byte[] decrypted = decrypt_AES_CBC(key, encrypted);
        System.out.println("Decrypted: " + new String(decrypted, "UTF-8"));
    }

    // AES_ECB加密：
    public static byte[] encrypt_AES_ECB(byte[] key, byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return cipher.doFinal(input);
    }

    // AES_ECB解密：
    public static byte[] decrypt_AES_ECB(byte[] key, byte[] input) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/ECB/PkCS5Padding");
        SecretKey keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        return cipher.doFinal(input);
    }

    // AES_CBC加密
    public static byte[] encrypt_AES_CBC(byte[] key, byte[] input) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey keySpec = new SecretKeySpec(key, "AES");
        // CBC模式需要生成一个16 bytes的initialization vector:
        SecureRandom sr = SecureRandom.getInstanceStrong();
        byte[] iv = sr.generateSeed(16);
        IvParameterSpec ivps = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivps);
        byte[] data = cipher.doFinal(input);
        // IV不需要保密，把IV和密文一起返回
        return join(iv, data);
    }

    // AES_CBC解密：
    public static byte[] decrypt_AES_CBC(byte[] key, byte[] input) throws Exception {
        // 把input分割成IV和密文：
        byte[] iv = new byte[16];
        byte[] data = new byte[input.length - 16];
        System.arraycopy(input, 0, iv, 0, 16);
        System.arraycopy(input, 16, data, 0, data.length);
        // 解密：
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivps = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivps);
        return cipher.doFinal(data);
    }

    public static byte[] join(byte[] bs1, byte[] bs2){
        byte[] r = new byte[bs1.length + bs2.length];
        System.arraycopy(bs1, 0, r, 0, bs1.length);
        System.arraycopy(bs2, 0, r, bs1.length, bs2.length);
        return r;
    }

}
