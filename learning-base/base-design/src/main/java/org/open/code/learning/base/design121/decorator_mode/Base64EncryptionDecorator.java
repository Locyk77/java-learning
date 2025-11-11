package org.open.code.learning.base.design121.decorator_mode;

import cn.hutool.core.codec.Base64;

/**
 * 具体装饰器：Base64
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class Base64EncryptionDecorator extends EncryptionDecorator {

    public Base64EncryptionDecorator(Encryption encryption) {
        super(encryption);
    }

    @Override
    public String encrypt(String data) {
        String encrypted = super.encrypt(data);
        return Base64.encode(encrypted);
    }

    @Override
    public String decrypt(String encryptedData) {
        String decrypted = Base64.decodeStr(encryptedData);
        return super.decrypt(decrypted);
    }
}
