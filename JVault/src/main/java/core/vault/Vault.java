package core.vault;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public interface Vault {
    void writeStream(Path sys_path, InputStream stream);
    byte[] readStream(Path internal_path, OutputStream stream);
    void addFile(Path sys_path, String internal_path);
    void removeFile(Path internal_path);
}
