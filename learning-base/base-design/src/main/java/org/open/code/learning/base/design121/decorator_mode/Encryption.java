package org.open.code.learning.base.design121.decorator_mode;

/**
 * 抽象组件
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public interface Encryption {
    String encrypt(String data);

    String decrypt(String encryptedData);
}
