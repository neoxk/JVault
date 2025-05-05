package core.io.fs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pointer {
    private long offset;
    private Integer length;
    private Integer padding_size;
}
