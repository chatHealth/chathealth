package chathealth.chathealth.dto.request;

import chathealth.chathealth.entity.board.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Setter
@Builder
public class BoardSearchDto {
    //게시판 검색 조건
    private Category category;
    private String title;
    private String content;
    private String writer;

    private static final int MAX_SIZE = 2000;

    @Builder.Default
    private Integer page = 0;
    @Builder.Default
    private Integer size = 20;

    public Integer getSize() {
        return size == null ? 20 : this.size;
    }

    public Integer getPage() {
        return page == null ? 0 : this.page;
    }


    public long getOffset() {
        size = this.size == null ? 20 : this.size;
        page = this.page == null ? 0 : this.page;
        return (long) max(0, page) * min(MAX_SIZE, size);
    }
}
