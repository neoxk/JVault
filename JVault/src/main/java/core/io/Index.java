package core.io;

import core.io.fs.FileProxy;
import core.io.fs.Pointer;
import lombok.Getter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;

public class Index {
    private static final int SECTOR_SIZE        = 4096;
    private static final int INDEX_START_SECTOR = 1;     // reserve sector #1…
    private static final int INDEX_SECTOR_COUNT = 3;     // …through sector #3 for the index

    private final Map<String,Pointer> entries = new LinkedHashMap<>();

    public Index() {
    }

    public Pointer getPointer(String path) {
        return entries.get(path);
    }

    public void addPointer(String path, Pointer pointer) {
        entries.put(path, pointer);
    }

    public void removePointer(String path) {
        entries.remove(path);
    }

    public List<String> listPaths() {
        return new ArrayList<>(entries.keySet());
    }

    public byte[] serialize() {
        try {
            StringBuilder sb = new StringBuilder();
            for (var e : entries.entrySet()) {
                sb.append(e.getKey())
                        .append(',')
                        .append(e.getValue().getOffset())
                        .append(',')
                        .append(e.getValue().getLength())
                        .append(',')
                        .append(e.getValue().getPadding_size())
                        .append('\n');
            }

            byte[] raw = sb.toString().getBytes(StandardCharsets.UTF_8);
            return Arrays.copyOf(raw, INDEX_SECTOR_COUNT * SECTOR_SIZE);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize index", e);
        }
    }

    public static Index load(byte[] raw) {
        Index idx = new Index();

        // find the first NUL byte (0) or end of data
        int len = 0;
        while (len < raw.length && raw[len] != 0) {
            len++;
        }

        // decode just the meaningful part as UTF-8 CSV
        String csv = new String(raw, 0, len, StandardCharsets.UTF_8);

        // parse each non-blank line
        for (String line : csv.split("\n")) {
            if (line.isBlank()) continue;

            // split into exactly 4 parts: path, offset, length, padding
            String[] parts = line.split(",", 4);
            if (parts.length != 4) {
                throw new IllegalArgumentException("Malformed index line: " + line);
            }

            String path    = parts[0];
            long   offset  = Long.parseLong(parts[1]);
            int    length  = Integer.parseInt(parts[2]);
            int    padding = Integer.parseInt(parts[3]);

            idx.entries.put(path, new Pointer(offset, length, padding));
        }

        return idx;
    }
}




