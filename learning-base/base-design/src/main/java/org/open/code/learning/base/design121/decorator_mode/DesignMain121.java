package org.open.code.learning.base.design121.decorator_mode;

/**
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class DesignMain121 {
    public static void main(String[] args) {
        String originalData = "Hello, Decorator Pattern for Hybrid Encryption!";
        System.out.println("原始数据: " + originalData);
        //基础加密->Base64加密->AES加密
        Encryption encryption = new AESEncryptionDecorator(new Base64EncryptionDecorator(new BaseEncryption()));
        String encryptedData = encryption.encrypt(originalData);
        System.out.println("加密后的数据: " + encryptedData);
        String decryptedData = encryption.decrypt(encryptedData);
        System.out.println("解密后的数据: " + decryptedData);
    }
}
