package core.encryption;

import java.io.InputStream;
import java.io.OutputStream;

public interface Cipher {
    byte[] encrypt(byte[] data);
    byte[] decrypt(byte[] data);
//    void encryptStream(InputStream in, OutputStream out);
//    void decryptStream(InputStream in, OutputStream out);
}
