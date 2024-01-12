package chathealth.chathealth.dto.request;


import chathealth.chathealth.entity.member.Ent;
import chathealth.chathealth.entity.post.*;
import jakarta.validation.constraints.NotBlank;
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
    //private Ent member;
    @NotBlank(message = "title입력")
    private String title;
    @NotBlank(message = "content입력")
    private String content;
    //@NotBlank(message = "con입력")
    private SymptomType symptom;
    private List<MaterialPost> materialList;
    private List<PicturePost> postImgList;
}
