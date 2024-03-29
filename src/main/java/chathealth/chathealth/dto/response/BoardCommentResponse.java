package chathealth.chathealth.dto.response;

import chathealth.chathealth.constants.Grade;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardCommentResponse {

    private Long id;
    private String content;

    private Long memberId;
    private String memberNickname;

    private String profile;
    private Grade grade;

    private LocalDateTime createdDate;

    private boolean isWriter;

    @Builder
    public BoardCommentResponse(Long id, String content, Long memberId, String memberNickname, String profile, LocalDateTime createdDate, Grade grade, boolean isWriter){
        this.id = id;
        this.content = content;
        this.memberId = memberId;
        this.memberNickname = memberNickname;
        this.profile = profile;
        this.createdDate = createdDate;
        this.grade = grade;
        this.isWriter = isWriter;
    }
}
