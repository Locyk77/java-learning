package org.open.code.learning.base.design121.decorator_mode;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * 具体装饰器：AES
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class AESEncryptionDecorator extends EncryptionDecorator {

    private byte[] key;

    private SymmetricCrypto aes;

    public AESEncryptionDecorator(Encryption encryption) {
        super(encryption);
        this.key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded(); // 随机生成密钥
        this.aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
    }

    @Override
    public String encrypt(String data) {
        String encrypted = super.encrypt(data);
        return aes.encryptHex(encrypted);
    }

    @Override
    public String decrypt(String encryptedData) {
        String decrypted = aes.decryptStr(encryptedData, CharsetUtil.CHARSET_UTF_8);
        return super.decrypt(decrypted);
    }
}
