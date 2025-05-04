package core.encryption.aes;

import core.encryption.ICipher;
import core.encryption.Password;
import core.encryption.Argon2Kdf;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.security.SecureRandom;

public class AESCtrCipher implements ICipher {
    private static final int SALT_LEN    = 16;
    private static final int IV_LEN      = 16;
    private static final int KEY_BITS    = 256;      // AES-256
    private static final int SECTOR_SIZE = 4096;     // per-sector random access

    private final SecureRandom rng = new SecureRandom();
    private final Password password;

    public AESCtrCipher(Password password) {
        this.password = password;
    }

    @Override
    public byte[] encrypt(byte[] fileData) {
        try {
            // Derive AES-256 key
            byte[] salt = new byte[SALT_LEN];
            rng.nextBytes(salt);
            byte[] key = Argon2Kdf.derive(
                    password.getPassword().toCharArray(),
                    salt,
                    KEY_BITS / 8
            );
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            out.write(salt);

            ByteBuffer in = ByteBuffer.wrap(fileData);
            byte[] sector = new byte[SECTOR_SIZE];

            while (in.hasRemaining()) {
                int len = Math.min(SECTOR_SIZE, in.remaining());
                in.get(sector, 0, len);

                // New random IV for this sector
                byte[] iv = new byte[IV_LEN];
                rng.nextBytes(iv);
                out.write(iv);

                Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
                byte[] ct = cipher.doFinal(sector, 0, len);

                out.write(ct, 0, len);
            }

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("AES-CTR encryption failed", e);
        }
    }

    @Override
    public byte[] decrypt(byte[] input) {
        try {
            ByteBuffer buf = ByteBuffer.wrap(input);
            byte[] salt = new byte[SALT_LEN];
            buf.get(salt);

            byte[] key = Argon2Kdf.derive(
                    password.getPassword().toCharArray(),
                    salt,
                    KEY_BITS / 8
            );
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            while (buf.hasRemaining()) {
                byte[] iv = new byte[IV_LEN];
                buf.get(iv);
                int len = Math.min(SECTOR_SIZE, buf.remaining());
                byte[] ct = new byte[len];
                buf.get(ct);

                Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
                cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
                byte[] pt = cipher.doFinal(ct);

                out.write(pt, 0, len);
            }

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("AES-CTR decryption failed", e);
        }
    }
}
