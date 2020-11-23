package com.itranswarp.learnjava;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Learn Java from https://www.liaoxuefeng.com/
 * 
 * @author liaoxuefeng
 */
public class Main {
	public static void main(String[] args) throws Exception {
//		// 注册BouncyCastle:
//		Security.addProvider(new BouncyCastleProvider());
//		// 按名称正常调用:
//		MessageDigest md = MessageDigest.getInstance("RipeMD160");
//		md.update("HelloWorld".getBytes("UTF-8"));
//		byte[] result = md.digest();
//		System.out.println(new BigInteger(1, result).toString(16));
		hmacMd5RecoverTest();
	}

	public static void hmacMD5Test() throws Exception {
		KeyGenerator keyGen = KeyGenerator.getInstance("HmacMD5");
		SecretKey key = keyGen.generateKey();
		// 打印随机生成的key
		byte[] skey = key.getEncoded();
		System.out.println(new BigInteger(skey).toString(16));
		Mac mac = Mac.getInstance("HmacMD5");
		mac.init(key);
		mac.update("HelloWorld".getBytes("UTF-8"));
		byte[] result = mac.doFinal();
		System.out.println(new BigInteger(result).toString(16));
	}

	public static void hmacMd5RecoverTest() throws Exception{
		String keyHex = "274dc2f2a2cc5f05aec2eba41e1294bec71584f45dea670bf720327c0242e519683d03d6a77934f1b2a093acf7b21516ef343a3ac26d13b6a8b41f991a1aebb";
		byte[] skey = keyHex.getBytes();
		SecretKey key = new SecretKeySpec(skey, "HmacMD5");
		Mac mac = Mac.getInstance("HmacMD5");
		mac.init(key);
		mac.update("HelloWorld".getBytes("UTF-8"));
		byte[] result = mac.doFinal();
		System.out.println(Arrays.toString(result));

		//[-76, -33, -122, 89, -60, 118, -64, 33, 21, 8, 120, -5, 79, 119, -96, 32]
	}
}
