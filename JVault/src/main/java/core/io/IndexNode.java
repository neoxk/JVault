package core.io;

import core.io.fs.Pointer;

import java.util.List;

public record IndexNode(
        String  name,
        Pointer pointer,
        List<IndexNode> children
) implements Comparable<IndexNode> {

    @Override public int compareTo(IndexNode o) {
        if (pointer == null && o.pointer != null) return -1;
        if (pointer != null && o.pointer == null) return  1;
        return name.compareToIgnoreCase(o.name);
    }
}
