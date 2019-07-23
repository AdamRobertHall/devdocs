package com.cdc.workcloud.manage.util;

import com.cdc.common.util.JsonMapper;
import com.cdc.workcloud.manage.domain.jxw.CryptoData;
import org.apache.commons.codec.binary.Base64;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CryptoUtil
 * @Description TODO
 * @Author lenovo
 * @Date 2019/2/26 9:26
 * @Version 1.0
 */
public class CryptoUtil {
    /**
     *
     * @param data  返回数据
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    public static CryptoData encrypt(String data,String publicKey) throws Exception
    {
        //1、产生AES密钥
        String keyString = AESSecurityUtil.generateKeyString();

    //2、用AES法加密数据
        String cryptograph = AESSecurityUtil.encrypt(keyString, data);

        //3、用RSA加密AES密钥
        JsonMapper mapper = new JsonMapper();
        byte[] finalKey = RSACoder.encryptByPublicKey(Base64.decodeBase64(keyString),publicKey);
//        System.out.print("用RSA加密AES密钥为:" + finalKey);
//        System.out.print("加密数据:" + cryptograph);
        System.out.println("aes密钥通过Rsa:"+Base64.encodeBase64String(finalKey));

        CryptoData cryptoData = new CryptoData();
        cryptoData.setKey(Base64.encodeBase64String(finalKey));
        System.out.println("aes加密后数据:"+cryptograph);
        cryptoData.setContent(cryptograph);

        //4、返回数据
        return cryptoData;
    }

    /**
     *
     * @param cryptoData 加密数据
     * @param privateKey rsa私钥
     * @return
     * @throws Exception
     */
    public static String decrypt(CryptoData cryptoData,String privateKey) throws Exception
    {
        //1、解密密钥
        byte[] decryptKey = RSACoder.decryptByPrivateKey(Base64.decodeBase64(cryptoData.getKey()),privateKey);

        //2、解密内容
        String decryptData = AESSecurityUtil.decrypt(Base64.encodeBase64String(decryptKey),cryptoData.getContent() );

        //3、返回
        return decryptData;

    }

//    public static void main(String[] args)throws Exception {
//        Map<String, Object> keyMap = RSACoder.initKey();
//       //公钥
//       String publicKey = RSACoder.getPublicKey(keyMap);
//        //私钥
//        String privateKey = RSACoder.getPrivateKey(keyMap);
//        //请求数据
//        Map<String, Object> requestMap = new HashMap<>();
//
//        requestMap.put("userId", "1,2,3,4");
//        requestMap.put("beginTime", new Date());
//        requestMap.put("endTime", new Date());
//        System.out.println("元数据："+requestMap);
//        JsonMapper mapper = new JsonMapper();
//
//        CryptoData cryptoData= CryptoUtil.encrypt(mapper.toJson(requestMap),publicKey);
//
//        System.out.println("加密后数据:"+cryptoData);
//
//        System.out.println("---解密--");
//
//        String result = CryptoUtil.decrypt(cryptoData,privateKey);
//
//        System.out.println("解密后数据:"+result);
//
//
//
//
//
//
//    }

    public static void main(String[] args)throws Exception {
//        String keyString = "9b8b7db9f43b4d79";
//        System.out.println(keyString);
//        System.out.println("----");
//        Map<String,Object> map = new HashMap<>();
//        map.put("beginTime", "20190306000000");
//        map.put("endTime", "20190307000000");
//        map.put("userId","13929537026,15994705655");
//        System.out.println();
//
//        //2、用AES法加密数据
//        JsonMapper mapper = new JsonMapper();
//        String aesStr = AESSecurityUtil.encrypt(keyString, mapper.toJson(map));
//        System.out.println(aesStr);
//        String keyString = "9b8b7db9f43b4d79";
//
//        String decryptData = AESSecurityUtil.decrypt(keyString,"kNsocl6YsnnpR3pg/M4dvAPnNtfwCfJs8+nXy3TDpAYWCgGy3zONkH3yVU9nimw5wfWc2y0ccr7Z/uHm4qa9Y7rqfCqIz1AI4gFyT1RySRMYiY906cwsvBqX9EKDEB8b");
//        System.out.println(decryptData);
    }

}
