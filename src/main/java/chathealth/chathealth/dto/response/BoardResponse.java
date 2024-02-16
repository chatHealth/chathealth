package chathealth.chathealth.dto.response;

import chathealth.chathealth.constants.Grade;
import chathealth.chathealth.entity.board.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardResponse {

    //게시글
    private long boardId;
    private String title;
    private String content;
    private Category category;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //작성자
    private String name;
    private String nickname;
    private Grade grade;
    private long memberId;
    private String profile;

    //조회수, 댓글수
    private int commentCount;
    private int hit;

    private Boolean isWriter;
}
