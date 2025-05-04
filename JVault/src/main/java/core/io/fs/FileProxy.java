package core.io.fs;

import core.encryption.Cipher;

import java.io.InputStream;
import java.io.OutputStream;

public class FileProxy{

    public FileProxy() {
    }

    public void write(byte[] data, Pointer pointer) {
    }

    public byte[] read(Pointer pointer) {
        return new byte[0];
    }

    public void writeStream(InputStream stream, Pointer pointer) {

    }

    public void readStream(OutputStream stream, Pointer pointer) {

    }
}
