package core.io.fs;

import java.io.InputStream;
import java.io.OutputStream;

public interface FileProxy {
    void write(byte[] data, Pointer pointer);
    byte[] read(Pointer pointer);
    void writeStream(InputStream stream, Pointer pointer);
    void readStream(OutputStream stream, Pointer pointer);
}
