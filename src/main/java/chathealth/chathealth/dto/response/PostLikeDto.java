package chathealth.chathealth.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class PostLikeDto {
    private Long id;
    private Long memberId;
    private Long postId;
    private String title;
    private String representativeImg;
    private String company;



}
