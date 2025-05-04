package core.io;

import core.io.fs.FileProxy;
import core.io.fs.Pointer;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class Header {

    private FileProxy file;

    private final Map <String, Pointer> pointers = Map.of(
            "magic", new Pointer(0, 6),
            "version", new Pointer(7, 1),
            "salt", new Pointer(8, 16)
    );

    private String magic   = "JVault";
    private String version = "1";

    @Getter
    private byte[] salt    = null;

    public Header(FileProxy file) {
        this.file = file;
    }


    protected void flush() {
         file.write(magic.getBytes(StandardCharsets.UTF_8), pointers.get("magic"));
         file.write(version.getBytes(StandardCharsets.UTF_8), pointers.get("version"));
         file.write(salt, pointers.get("salt"));
    }

    public void setSalt(byte[] salt) {
        if (salt.length != 16) {
            throw new IllegalArgumentException("Salt must be 16 bytes long");
        }
        this.salt = salt;
    }
}
