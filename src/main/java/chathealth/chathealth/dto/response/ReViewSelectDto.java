package chathealth.chathealth.dto.response;

import chathealth.chathealth.entity.Helpful;
import chathealth.chathealth.entity.PictureReView;
import chathealth.chathealth.entity.ReComment;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.entity.post.Post;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class ReViewSelectDto {
    private Long id;
    private Long member;
    private String nickName;
    private String profile;
    private String content;
    private double score;
    private String createdDate;
    private List<String> pictureReView;

    private Integer same;
    public Integer sameclass(Long member,CustomUserDetails login){
        if(login!=null){
            if(member.equals(login.getLoggedMember().getId())){
                return 5;
            }else {
                return 1;
            }
        }return 2;
    }
}
