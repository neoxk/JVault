package core.encryption;

public class CipherFactory {
    private CipherType cipher;
    private Password password;

    public CipherFactory(CipherType cipher, Password password) {
        this.cipher = cipher;
        this.password = password;
    }

    public Cipher getCipher() {
        switch (cipher) {
            case CipherType.AES_256_GCM:
               return new AESGCMCipher(256, password);
        }

        throw new IllegalArgumentException("Unsupported cipher type: " + cipher);
    }

}
