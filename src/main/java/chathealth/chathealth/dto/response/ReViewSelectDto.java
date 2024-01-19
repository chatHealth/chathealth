package chathealth.chathealth.dto.response;

import chathealth.chathealth.entity.Helpful;
import chathealth.chathealth.entity.PictureReView;
import chathealth.chathealth.entity.ReComment;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.entity.post.Post;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReViewSelectDto {
    private String nickName;
    private String profile;
    private String content;
    private double score;
    private String createdDate;
    private List<String> pictureReView;

}
