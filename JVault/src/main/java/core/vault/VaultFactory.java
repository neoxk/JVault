package core.vault;

import core.encryption.Cipher;
import core.encryption.Password;
import core.encryption.aes.AESGCMCipher;
import core.io.fs.EncryptedFileProxy;
import core.io.fs.FileProxy;

import java.nio.file.Path;

public class VaultFactory {
    public static int ENCRYPTED_FILE_TYPE = 0;

    public Vault open(Path sys_path, Path password) {

    }

    public Vault create(Path sys_path, Password password) {
        Cipher cipher = new AESGCMCipher(255, password);
        FileProxy fileProxy = new EncryptedFileProxy(cipher);

        return new EncryptedFileVault(fileProxy);
    }
}
