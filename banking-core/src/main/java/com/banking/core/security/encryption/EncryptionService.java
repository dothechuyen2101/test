package com.banking.core.security.encryption;

/**
 * Interface for data encryption and decryption services.
 * Implementations may use AES, Vault Transit, or other algorithms.
 */
public interface EncryptionService {

    /**
     * Encrypt a plaintext string.
     *
     * @param plaintext the data to encrypt
     * @return the encrypted (base64-encoded) data
     */
    String encrypt(String plaintext);

    /**
     * Decrypt an encrypted string.
     *
     * @param ciphertext the encrypted (base64-encoded) data
     * @return the decrypted plaintext
     */
    String decrypt(String ciphertext);

    /**
     * Encrypt data using a specific key alias.
     */
    String encrypt(String plaintext, String keyAlias);

    /**
     * Decrypt data using a specific key alias.
     */
    String decrypt(String ciphertext, String keyAlias);

    /**
     * Encrypt raw bytes.
     */
    byte[] encryptBytes(byte[] data);

    /**
     * Decrypt raw bytes.
     */
    byte[] decryptBytes(byte[] encryptedData);
}
