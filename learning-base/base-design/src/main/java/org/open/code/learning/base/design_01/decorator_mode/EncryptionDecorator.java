package org.open.code.learning.base.design_01.decorator_mode;

/**
 * 抽象装饰器
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public abstract class EncryptionDecorator implements Encryption {

    protected Encryption encryption;

    public EncryptionDecorator(Encryption encryption) {
        this.encryption = encryption;
    }

    @Override
    public String encrypt(String data) {
        return encryption.encrypt(data);
    }

    @Override
    public String decrypt(String encryptedData) {
        return encryption.decrypt(encryptedData);
    }
}
