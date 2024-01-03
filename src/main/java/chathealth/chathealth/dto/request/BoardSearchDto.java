package chathealth.chathealth.dto.request;

import chathealth.chathealth.entity.borad.Category;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class BoardSearchDto {
    //게시판 검색 조건
    private Category category;
    private String title;
    private String content;
    private String writer;

    @Builder
    public BoardSearchDto(Category category, String title, String content, String writer) {
        this.category = category;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
