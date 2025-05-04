package core.io.fs;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public class FileProxy{
    private Path sys_path;

    public FileProxy(Path sys_path) {
        this.sys_path = sys_path;
    }

    public void write(byte[] data, Pointer pointer) {
    }

    public byte[] read(Pointer pointer) {
        return new byte[0];
    }

    public byte[] readAll() {
        return new byte[0];
    }


    public void writeStream(InputStream stream, Pointer pointer) {

    }

    public void readStream(OutputStream stream, Pointer pointer) {

    }

    public int getSize() {
        return 0;
    }

    public void purge() {

    }
}
