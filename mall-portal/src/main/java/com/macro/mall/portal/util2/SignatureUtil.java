package com.macro.mall.portal.util2;

import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;

import java.math.BigDecimal;

/*************************************************

Desc: 数据签名模块，用于将从服务器获得的临时使用的数据保存于客户端，当客户端后续使用这些数据请求服务器时，
	通过验证数据签名是否有效判定是否用户篡改过数据，减少数据库查询，参考“https://blog.csdn.net/qq_2976139
	5/article/details/103892650”

Author: ng123123

Date: 2020/10/20

QQ交流群: 892951935

**************************************************/ 

public class SignatureUtil {
	private static String salt = "123456789abcde"; // 68e8fb21334aa71b
	private static String securityKey = "this is password";
	
	public static String productSkuSignature(Long productId, Long productSkuId, BigDecimal price) {
		//System.out.println( KeyGenerators.string().generateKey() );
		String content = String.valueOf(productId) + "-" + String.valueOf(productSkuId) + "-" + price.toString();
		TextEncryptor textEncryptor = Encryptors.text(securityKey, salt);
		String encrypted = textEncryptor.encrypt(content);
		/*
		String decrypted = textEncryptor.decrypt(encrypted); // 解密
        System.out.println(decrypted); // 明文
		*/
		return encrypted;
	}
}