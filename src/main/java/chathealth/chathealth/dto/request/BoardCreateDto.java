package chathealth.chathealth.dto.request;

import chathealth.chathealth.entity.borad.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardCreateDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
    @NotNull(message = "카테고리를 선택해주세요.")
    private Category category;

    @Builder
    public BoardCreateDto(String title, String content, Category category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }
}