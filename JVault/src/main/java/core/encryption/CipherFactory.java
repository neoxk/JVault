package core.encryption;

import core.encryption.aes.AESCtrCipher;

public class CipherFactory {
    private final Password password;

    public CipherFactory(Password password) {
        this.password = password;
    }

    public ICipher getCipher() {
        return new AESCtrCipher(password);
    }
}
