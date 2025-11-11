package org.open.code.learning.base.design121.decorator_mode;

/**
 * 具体组件
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class BaseEncryption implements Encryption {


    @Override
    public String encrypt(String data) {
        // 具体实现加密逻辑，没有做
        return data;
    }

    @Override
    public String decrypt(String encryptedData) {
        // 具体实现解密逻辑，没有做
        return encryptedData;
    }
}
