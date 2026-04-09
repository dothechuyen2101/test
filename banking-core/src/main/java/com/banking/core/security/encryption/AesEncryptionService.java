package com.banking.core.security.encryption;

import com.banking.common.exception.TechnicalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES-256-GCM encryption service implementation.
 * Uses authenticated encryption for data confidentiality and integrity.
 */
@Service
public class AesEncryptionService implements EncryptionService {

    private static final Logger log = LoggerFactory.getLogger(AesEncryptionService.class);

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128;

    private final SecretKey defaultKey;
    private final SecureRandom secureRandom;

    public AesEncryptionService(
            @Value("${banking.security.encryption.key:0123456789abcdef0123456789abcdef}") String keyHex) {
        byte[] keyBytes = hexStringToByteArray(keyHex);
        this.defaultKey = new SecretKeySpec(keyBytes, "AES");
        this.secureRandom = new SecureRandom();
    }

    @Override
    public String encrypt(String plaintext) {
        return encrypt(plaintext, null);
    }

    @Override
    public String encrypt(String plaintext, String keyAlias) {
        try {
            byte[] encrypted = encryptBytes(plaintext.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            log.error("Encryption failed", e);
            throw TechnicalException.encryptionError("encrypt", e);
        }
    }

    @Override
    public String decrypt(String ciphertext) {
        return decrypt(ciphertext, null);
    }

    @Override
    public String decrypt(String ciphertext, String keyAlias) {
        try {
            byte[] decoded = Base64.getDecoder().decode(ciphertext);
            byte[] decrypted = decryptBytes(decoded);
            return new String(decrypted);
        } catch (Exception e) {
            log.error("Decryption failed", e);
            throw TechnicalException.encryptionError("decrypt", e);
        }
    }

    @Override
    public byte[] encryptBytes(byte[] data) {
        try {
            byte[] iv = new byte[GCM_IV_LENGTH];
            secureRandom.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, defaultKey, parameterSpec);

            byte[] encryptedData = cipher.doFinal(data);

            // Prepend IV to encrypted data: [IV (12 bytes)] + [encrypted data + tag]
            ByteBuffer byteBuffer = ByteBuffer.allocate(GCM_IV_LENGTH + encryptedData.length);
            byteBuffer.put(iv);
            byteBuffer.put(encryptedData);

            return byteBuffer.array();
        } catch (Exception e) {
            throw TechnicalException.encryptionError("encryptBytes", e);
        }
    }

    @Override
    public byte[] decryptBytes(byte[] encryptedData) {
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(encryptedData);

            byte[] iv = new byte[GCM_IV_LENGTH];
            byteBuffer.get(iv);

            byte[] cipherBytes = new byte[byteBuffer.remaining()];
            byteBuffer.get(cipherBytes);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, defaultKey, parameterSpec);

            return cipher.doFinal(cipherBytes);
        } catch (Exception e) {
            throw TechnicalException.encryptionError("decryptBytes", e);
        }
    }

    private static byte[] hexStringToByteArray(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}
