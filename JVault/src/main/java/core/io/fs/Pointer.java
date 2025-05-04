package core.io.fs;

import lombok.Data;

@Data
public class Pointer {
    private int offset;
    private int length;
}
