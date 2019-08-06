package com.changfa.frame.service.jpa.util;


import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.changfa.frame.core.exception.SysException;
import com.changfa.frame.core.util.Base64;


/**
 * 加解密
 *
 * @author baixd
 * @version 1.0 Oct 23, 2014
 */
public final class EncryptUtils {

    // 3DES密钥
    static String secretKey = "1!QAZ2@WSXCDE#3$4RFVBGT%5^6YHNMJU7&8*IK<.LO9(0P";
    // 向量
    private final static String IV = "12481632";

    private final static String ENCODING = "utf-8";

    /**
     * 3DES加密
     *
     * @param plainText
     * @return
     * @throws Exception
     */
    public static String encrypt3DESECB(String plainText) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(ENCODING));
        return Base64.encode(encryptData);

    }

    /**
     * 3DES解密
     *
     * @param encryptText
     * @throws SysException
     * @throws Exception
     */
    public static String decrypt3DESECB(String encryptText) throws Exception {
        String res = null;
        try {
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory
                    .getInstance("desede");
            Key deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
            byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));
            res = new String(decryptData, ENCODING);
        } catch (Exception e) {
            throw new SysException("mobile", "解密过程出现错误！", e);
        }

        return res;
    }

    public static void main(String[] args) {
        String s1 = "asdfa";
        try {
            System.out.println(decrypt3DESECB(encrypt3DESECB(s1)));
        } catch (SysException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
