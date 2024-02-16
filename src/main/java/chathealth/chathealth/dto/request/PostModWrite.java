package chathealth.chathealth.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostModWrite {
    @NotBlank(message = "title입력")
    private String title;
    @NotBlank(message = "content입력")
    private String content;
    //@NotBlank(message = "con입력")
    private Long symptom;
    @NotBlank(message = "성분을 선택해주세요")
    private List<Long> materialList;
    private List<String> postImgList;
}
