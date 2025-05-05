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

    private String magic   = "JVault";
    private String version = "1";

    @Getter
    private byte[] salt    = null;

    public Header(FileProxy file) {
        this.file = file;
    }


    public void setSalt(byte[] salt) {
        if (salt.length != 16) {
            throw new IllegalArgumentException("Salt must be 16 bytes long");
        }
        this.salt = salt;
    }

    public byte[] serialize() {

    }
}
