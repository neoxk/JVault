package core.io.fs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileProxy{
    private Path sys_path;
    private

    public FileProxy(Path sys_path) {
        this.sys_path = sys_path;
        try {
            if (Files.notExists(sys_path)) {
                Files.createDirectories(sys_path.getParent());
                Files.createFile(sys_path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize file at " + sys_path, e);
        }
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
