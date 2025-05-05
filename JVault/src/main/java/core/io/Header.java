package core.io;

import core.io.fs.FileProxy;
import core.io.fs.Pointer;
import lombok.Getter;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Header {
    private String magic   = "JVault";
    private String version = "1";
    private final int SECTOR_SIZE = 4096;

    @Getter
    private byte[] salt    = new byte[16];


    public void setSalt(byte[] salt) {
        if (salt.length != 16) {
            throw new IllegalArgumentException("Salt must be 16 bytes long");
        }
        this.salt = salt;
    }

    public byte[] serialize() {
        if (salt == null) {
            throw new IllegalStateException("Salt has not been set");
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             DataOutputStream out  = new DataOutputStream(baos))
        {
            byte[] magicBytes = magic.getBytes(StandardCharsets.US_ASCII);
            out.writeInt(magicBytes.length);
            out.write(magicBytes);

            byte[] verBytes = version.getBytes(StandardCharsets.US_ASCII);
            out.writeInt(verBytes.length);
            out.write(verBytes);

            out.write(salt);

            out.flush();

            byte[] raw = baos.toByteArray();
            if (raw.length > SECTOR_SIZE) {
                throw new IllegalStateException(
                        "Header data (" + raw.length + " bytes) exceeds sector size");
            }
            return Arrays.copyOf(raw, SECTOR_SIZE);
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize header", e);
        }
    }

    public static Header load(byte[] headerBytes) {
        if (headerBytes == null) {
            throw new IllegalArgumentException("Header bytes cannot be null");
        }
        try (ByteArrayInputStream bais = new ByteArrayInputStream(headerBytes);
             DataInputStream in = new DataInputStream(bais)) {

            // 1) Read magic
            int magicLen = in.readInt();
            if (magicLen < 0 || magicLen > headerBytes.length) {
                throw new IllegalArgumentException("Corrupted header (bad magic length)");
            }
            byte[] magicBytes = new byte[magicLen];
            in.readFully(magicBytes);
            String magic = new String(magicBytes, StandardCharsets.US_ASCII);

            // Validate magic
            if (!"JVault".equals(magic)) {
                throw new IllegalArgumentException("Invalid file format: wrong magic '" + magic + "'");
            }

            // 2) Read version
            int versionLen = in.readInt();
            if (versionLen < 0 || versionLen > headerBytes.length) {
                throw new IllegalArgumentException("Corrupted header (bad version length)");
            }
            byte[] versionBytes = new byte[versionLen];
            in.readFully(versionBytes);
            String version = new String(versionBytes, StandardCharsets.US_ASCII);

            // 3) Read salt
            byte[] salt = new byte[16];
            in.readFully(salt);

            Header h = new Header();
            h.magic   = magic;
            h.version = version;
            h.setSalt(salt);

            return h;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load header", e);
        }
    }

}
