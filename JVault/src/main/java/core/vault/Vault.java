package core.vault;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.List;

public interface Vault {
    void addFile(Path sys_path, String internal_path);
    void removeFile(Path internal_path);
    void decryptFile(String sys_path, String internal_path);
    List<String> getPaths();
    void save();
}
