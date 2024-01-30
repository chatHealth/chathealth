package chathealth.chathealth.dto.response.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MyPostDto {
    private String title;
    private Long postId;
    private Long memberId;
    private String createdDate;

}
