package core.io;

import java.io.InputStream;

public interface VaultIO {
    public void writeProp(HeaderProp prop, String value);
    public String readProp(HeaderProp prop);
    public Index readIndex();
    public void writeFile();
    public byte[] readFile();
}
