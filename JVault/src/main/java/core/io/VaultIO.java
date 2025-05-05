package core.io;

import core.io.fs.Pointer;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface VaultIO {
    public List<String> getPaths();
    public void writeFile(byte[] data, String path);
    public byte[] readFile(String path);
    public void removeFile(String path);
    public void saveMeta();
}
