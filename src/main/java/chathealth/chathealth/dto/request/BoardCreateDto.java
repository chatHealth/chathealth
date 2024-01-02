package chathealth.chathealth.dto.request;

import chathealth.chathealth.entity.borad.Category;
import lombok.Getter;

@Getter
public class BoardCreateDto {

    private String title;
    private String content;
    private Category category;

}
