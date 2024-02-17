package chathealth.chathealth.dto.response.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class MyReviewDto {
    private Long memberId;
    private Long postId;
    private String title;
    private String content;
    private String createdDate;
}
