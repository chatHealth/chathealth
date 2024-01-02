package chathealth.chathealth.dto.response;

import chathealth.chathealth.entity.borad.Category;
import chathealth.chathealth.entity.member.Grade;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class BoardResponse {

    //게시글
    private long boardId;
    private String title;
    private String content;
    private Category category;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //작성자
    private String nickname;
    private Grade grade;
    private long memberId;
    private String profile;

    //조회수, 댓글수
    private int commentCount;
    private int hit;
}
