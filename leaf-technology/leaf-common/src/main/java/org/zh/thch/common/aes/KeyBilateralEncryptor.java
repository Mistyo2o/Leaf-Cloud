package org.zh.thch.common.aes;

/**
 * 使用密钥的双向加密器
 *
 * @author fangen
 */
public interface KeyBilateralEncryptor{

    String encrypt(Object source, Object key);

    String decrypt(String encryptedText, Object key);
}
