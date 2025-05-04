package core.vault;

import core.io.fs.FileProxy;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public class EncryptedFileVault implements Vault {
    private FileProxy file;

    public EncryptedFileVault(FileProxy file) {
        this.file = file;
    }

    @Override
    public void writeStream(Path internal_path, InputStream stream) {

    }

    @Override
    public byte[] readStream(Path internal_path, OutputStream stream) {
        return new byte[0];
    }

    @Override
    public void addFile(Path sys_path, String internal_path) {

    }

    @Override
    public void removeFile(Path internal_path) {

    }
}
