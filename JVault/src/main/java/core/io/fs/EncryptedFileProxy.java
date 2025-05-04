package core.io.fs;

import core.encryption.Cipher;

import java.io.InputStream;
import java.io.OutputStream;

public class EncryptedFileProxy implements FileProxy {
    private Cipher cipher;

    public EncryptedFileProxy(Cipher cipher) {
        this.cipher = cipher;
    }
    @Override
    public void write(byte[] data, Pointer pointer) {
    }

    @Override
    public byte[] read(Pointer pointer) {
        return new byte[0];
    }

    @Override
    public void writeStream(InputStream stream, Pointer pointer) {

    }

    @Override
    public void readStream(OutputStream stream, Pointer pointer) {

    }
}
