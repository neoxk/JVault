package core.vault;

import core.encryption.ICipher;
import core.encryption.Password;
import core.encryption.aes.AESCtrCipher;
import core.io.EncryptedVaultIO;
import core.io.Header;
import core.io.Index;
import core.io.fs.FileProxy;

import java.nio.file.Path;

public class VaultFactory {
    public static int ENCRYPTED_FILE_TYPE = 0;

    public Vault open(Path sys_path, Password password) {
        ICipher cipher = new AESCtrCipher(password);
        FileProxy fileProxy = new FileProxy(sys_path);
        EncryptedVaultIO vaultIO = new EncryptedVaultIO(fileProxy, cipher, true);
        return new FileVault(vaultIO);
    }

    public Vault create(Path sys_path, Password password) {
        ICipher cipher = new AESCtrCipher(password);
        FileProxy fileProxy = new FileProxy(sys_path);
        EncryptedVaultIO vaultIO = new EncryptedVaultIO(fileProxy, cipher, false);

        return new FileVault(vaultIO);
    }
}
