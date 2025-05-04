package core.io;

import java.io.InputStream;

public interface VaultIO {
    public IndexNode getIndexRoot();
    public void writeFile(byte[] data, String path);
    public byte[] readFile(String path);
    public void format();
}
