package chathealth.chathealth.dto.response;

import chathealth.chathealth.dto.response.member.CustomUserDetails;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

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
    private String name;
    private String profile;
    private String content;
    private double score;
    private LocalDateTime createdDate;
    private List<String> pictureReView;
    private Long helpful;
    private Long helpfulCheck;

    private Integer same;
    public Integer sameclass(Long member, CustomUserDetails login){
        if(login!=null){
            if(member.equals(login.getLoggedMember().getId())){
                return 5;
            }else {
                return 1;
            }
        }return 2;
    }
}
