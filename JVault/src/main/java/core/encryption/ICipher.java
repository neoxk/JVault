package core.encryption;


public interface ICipher {
    byte[] encrypt(byte[] data) throws Exception;
    byte[] decrypt(byte[] data) throws Exception;
//    void encryptStream(InputStream in, OutputStream out);
//    void decryptStream(InputStream in, OutputStream out);
}
