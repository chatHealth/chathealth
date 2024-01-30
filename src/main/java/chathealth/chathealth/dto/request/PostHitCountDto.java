package chathealth.chathealth.dto.request;


import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.entity.post.Post;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostHitCountDto {
    private long post;
    private long member;
    private String createDate;
}
