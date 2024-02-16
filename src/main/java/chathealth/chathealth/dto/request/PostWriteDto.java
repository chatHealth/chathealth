package chathealth.chathealth.dto.request;


import chathealth.chathealth.entity.member.Ent;
import chathealth.chathealth.entity.post.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostWriteDto {
    private Long id;
    //private Ent member;
    @Size(max=30, message = "내용은 30자 이내로 작성해주세요.")
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
