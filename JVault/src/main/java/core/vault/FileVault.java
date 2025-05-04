package core.vault;

import core.io.VaultIO;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public class FileVault implements Vault {
    private VaultIO vaultIO;

    public FileVault(VaultIO vaultIO) {
        this.vaultIO = vaultIO;
    }


    @Override
    public void addFile(Path sys_path, String internal_path) {

    }

    @Override
    public void removeFile(Path internal_path) {

    }

    @Override
    public void decryptFile(String internal_path) {

    }
}
