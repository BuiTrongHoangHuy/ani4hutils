package site.ani4h.shared.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingSearch {
    private String cursor;
    private String nextCursor;
    private int pageSize = 10;
    private int page = 1;
    public int getOffset() {
        return (page - 1) * pageSize;
    }
}

