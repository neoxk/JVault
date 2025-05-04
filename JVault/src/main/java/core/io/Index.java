package core.io;

import core.io.fs.FileProxy;
import core.io.fs.Pointer;
import lombok.Getter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Index {

    @Getter
    private final IndexNode root;

    private FileProxy file;

    public Index(FileProxy file) {
        this.root = new IndexNode("/", null, new ArrayList<>());
        this.file = file;
    }

    public Pointer getPointer(String path) {
        String[] parts = path.split("/");
        IndexNode current = root;
        for (String part : parts) {
            if (current == null) return null;
            current = current.children().stream()
                    .filter(node -> node.name().equals(part))
                    .findFirst()
                    .orElse(null);
        }

        return current.pointer();
    }

    public void addPointer(String path, Pointer pointer) {
        IndexNode node = new IndexNode(path, pointer, new ArrayList<>());

        if (path == null || path.isBlank()) {
            root.children().add(node);
            return;
        }

        String[] parts = path.split("/");
        IndexNode current = root;

        for (String part : parts) {
            IndexNode next = current.children().stream()
                    .filter(n -> n.name().equals(part))
                    .findFirst()
                    .orElse(null);

            if (next == null) {
                next = new IndexNode(part, null, new ArrayList<>());
                current.children().add(next);
            }

            current = next;
        }

        current.children().add(node);
    }

    private byte[] serialize() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             DataOutputStream out  = new DataOutputStream(baos)) {

            List<IndexNode> files = collectFiles();
            out.writeInt(files.size());

            for (IndexNode n : files) {
                out.writeUTF(buildPath(n));
                out.writeLong(n.pointer().getOffset());
                out.writeInt(n.pointer().getLength());
            }
            return baos.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Should never happen", e);
        }
    }

    public static Index fromFile(FileProxy file) {
        Index idx = new Index(file);

        byte[] data = new byte[100];
        //TODO implement reading from file

        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(data))) {
            int count = in.readInt();
            for (int i = 0; i < count; i++) {
                String path   = in.readUTF();
                long   offset = in.readLong();
                int    length = in.readInt();
                idx.addPointer(path, new Pointer(offset, length));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Corrupt index data", e);
        }
        return idx;
    }


    private List<IndexNode> collectFiles() {
        List<IndexNode> acc = new ArrayList<>();
        walk(root, n -> { if (n.pointer() != null) acc.add(n); });
        return acc;
    }

    private void walk(IndexNode node, Consumer<IndexNode> visit) {
        visit.accept(node);
        node.children().forEach(child -> walk(child, visit));
    }

    private String buildPath(IndexNode node) {
        StringBuilder sb = new StringBuilder();
        buildPathRec(node, sb);
        return sb.toString();
    }

    private void buildPathRec(IndexNode node, StringBuilder sb) {
        if (node == root) return;
        buildPathRec(findParent(node), sb);
        if (sb.length() > 0) sb.append('/');
        sb.append(node.name());
    }

    private IndexNode findParent(IndexNode child) {
        return findParentRec(root, child);
    }
    private IndexNode findParentRec(IndexNode current, IndexNode target) {
        for (IndexNode c : current.children()) {
            if (c == target) return current;
            IndexNode found = findParentRec(c, target);
            if (found != null) return found;
        }
        return null;
    }

    public void flush() {
        byte[] data = serialize();
        file.write(new byte[12288-4096], new Pointer(4096, 12288));
        //TODO hardcoded index size and header size
        file.write(data, new Pointer(4096, data.length));
    }


}
