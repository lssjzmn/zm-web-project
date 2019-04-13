package com.lssj.zmn.server.app.utils.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Lance Chen
 */
public class SecurityUtil {

    private static Map<String, String> MAPPING = new HashMap<String, String>();
    private static Map<String, String> RESERVE_MAPPING = new HashMap<String, String>();
    private static final int ENCRYPT_TIMES = 7;
    private static final int SEED = 19880408;

    static {
        MAPPING.put("0", "7");
        MAPPING.put("1", "9");
        MAPPING.put("2", "0");
        MAPPING.put("3", "4");
        MAPPING.put("4", "8");
        MAPPING.put("5", "1");
        MAPPING.put("6", "2");
        MAPPING.put("7", "3");
        MAPPING.put("8", "5");
        MAPPING.put("9", "6");

        for (Map.Entry<String, String> entry : MAPPING.entrySet()) {
            RESERVE_MAPPING.put(entry.getValue(), entry.getKey());
        }
    }

    private SecurityUtil() {
    }

    /**
     * Parse a string to MD5 32 string.
     *
     * @param src The src string
     * @return Return the MD5 32 string
     */
    public static String toMD5(String src) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] result = messageDigest.digest(src.getBytes("UTF-8"));
            StringBuilder md5Str = new StringBuilder();

            for (int i = 0; i < result.length; i++) {
                if (Integer.toHexString(0xFF & result[i]).length() == 1) {
                    md5Str.append("0").append(Integer.toHexString(0xFF & result[i]));
                } else {
                    md5Str.append(Integer.toHexString(0xFF & result[i]));
                }
            }
            return md5Str.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SecurityUtil.class.getName()).log(Level.SEVERE, null, ex);
            return src;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SecurityUtil.class.getName()).log(Level.SEVERE, null, ex);
            return src;
        }
    }

    public static String encodeEmail(String email) {
        Date now = new Date();
        String randomStr = StringUtil.createRandomString(10);
        String encodeString = email + "####" + now.getTime() + "####" + randomStr;
        try {
            byte[] encodeBytes = Base64.encodeBase64(encodeString.getBytes("UTF-8"));
            return new String(encodeBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decodeEmail(String encryptedString, long expireTime) {
        try {
            String decodeString = new String(Base64.decodeBase64(encryptedString.getBytes("UTF-8")));
            String[] decodeArray = decodeString.split("####");
            String email = decodeArray[0];
            String date = decodeArray[1];
            String randomStr = decodeArray[2];

            long datetime = Long.valueOf(date);
            long now = new Date().getTime();
            if ((now - datetime) > expireTime) {
                throw new RuntimeException("验证码已过期!");
            }
            if (randomStr == null || randomStr.length() != 10) {
                throw new RuntimeException("验证码无效！");
            }
            return email;
        } catch (Exception e) {
            throw new RuntimeException("验证码无效，请重新获取！");
        }
    }

    public static String encodeValidationCode(String orderLineNo) {

        String str = orderLineNo.length() + orderLineNo;
        for (int i = 0; i < 4; i++) {
            Random random = new Random();
            str += random.nextInt(10);
        }
        for (int i = 0; i < ENCRYPT_TIMES; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < str.length(); j++) {
                char c = str.charAt(j);
                String s = String.valueOf(c);
                sb.append(MAPPING.get(s));
            }
            str = sb.toString();
        }

        return str;
    }

    public static String decodeValidationCode(String code) {
        String str = code;
        for (int i = 0; i < ENCRYPT_TIMES; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < str.length(); j++) {
                char c = str.charAt(j);
                String s = String.valueOf(c);
                sb.append(RESERVE_MAPPING.get(s));
            }
            str = sb.toString();
        }
        int length = Integer.valueOf(str.substring(0, 1));
        return str.substring(1, length + 1);
    }


    public static String  test(){
        String encodeStr= DigestUtils.md5Hex("dfffffffffffffffffffffff");
        return encodeStr;

    }
}
