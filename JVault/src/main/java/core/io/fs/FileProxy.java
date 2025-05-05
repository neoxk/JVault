package core.io.fs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileProxy{
    private Path sys_path;
    private final int SECTOR_SIZE = 4096;

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
        long startPos = pointer.getOffset() * SECTOR_SIZE;
        long totalBytes = (long) pointer.getLength() * SECTOR_SIZE;

        if (data.length != totalBytes) {
            throw new IllegalArgumentException(
                    "Data length (" + data.length +
                            ") must equal pointer.length * SECTOR_SIZE (" + totalBytes + ")");
        }

        try (SeekableByteChannel chan = Files.newByteChannel(
                sys_path,
                StandardOpenOption.WRITE,
                StandardOpenOption.CREATE))
        {
            chan.position(startPos);
            ByteBuffer buf = ByteBuffer.wrap(data);
            while (buf.hasRemaining()) {
                chan.write(buf);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write sectors at offset="
                    + pointer.getOffset(), e);
        }
    }


    public byte[] read(Pointer pointer) {
        long startPos = pointer.getOffset() * SECTOR_SIZE;
        int totalBytes = pointer.getLength() * SECTOR_SIZE;
        ByteBuffer buf = ByteBuffer.allocate(totalBytes);

        try (SeekableByteChannel chan = Files.newByteChannel(
                sys_path,
                StandardOpenOption.READ))
        {
            chan.position(startPos);
            while (buf.hasRemaining()) {
                int n = chan.read(buf);
                if (n < 0) break;  // EOF
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read sectors at offset="
                    + pointer.getOffset(), e);
        }

        return buf.array();
    }

    public byte[] readAll() {
        return new byte[0];
    }

    public void writeSector(byte[] data, Pointer pointer) {}

    public int getSize() {
        return 0;
    }

    public void purge() {

    }
}
