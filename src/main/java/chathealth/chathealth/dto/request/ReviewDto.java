package chathealth.chathealth.dto.request;


import chathealth.chathealth.entity.Helpful;
import chathealth.chathealth.entity.PictureReView;
import chathealth.chathealth.entity.ReComment;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.entity.post.PicturePost;
import chathealth.chathealth.entity.post.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReviewDto {
    private Long post;
    private Long member;
    @Size(max=300, message = "내용은 300자 이내로 작성해주세요.")
    private String content;
    private double score;
    private List<String> pictureReList;
}
