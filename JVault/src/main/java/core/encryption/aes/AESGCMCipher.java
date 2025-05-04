package core.encryption.aes;

import core.encryption.Cipher;
import core.encryption.Password;
import lombok.AllArgsConstructor;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;

@AllArgsConstructor
public class AESGCMCipher implements Cipher {
    private int keySize;
    private Password password;

    private final String SHA_CRYPT = "SHA-256";
    private final String AES_ALGORITHM = "AES";
    private final String AES_TRANSFORMATION = "AES/GCM/NoPadding";
    private final Integer IV_LENGTH = 12;
    private final Integer TAG_LENGTH = 16;

    @Override
    public byte[] encrypt(byte[] data) {
        //Implement
        return data;
    }

    @Override
    public byte[] decrypt(byte[] data) {
        //Implement
        return data;
    }

    @Override
    public void encryptStream(InputStream in, OutputStream out) {
        //implement
    }

    @Override
    public void decryptStream(InputStream in, OutputStream out) {
        //implement
    }
}
