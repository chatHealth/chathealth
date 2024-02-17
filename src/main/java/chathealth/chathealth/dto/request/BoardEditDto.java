package chathealth.chathealth.dto.request;

import chathealth.chathealth.entity.board.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardEditDto {

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max=50, message = "제목은 50자 이내로 작성해주세요.")
    public String title;
    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max=10000, message = "내용은 10,000자 이내로 작성해주세요.")
    public String content;
    @NotNull(message = "카테고리를 선택해주세요.")
    public Category category;

    @Builder
    public BoardEditDto(String title, String content, Category category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }
}
