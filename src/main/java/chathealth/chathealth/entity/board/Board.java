package chathealth.chathealth.entity.board;

import chathealth.chathealth.dto.request.BoardEditDto;
import chathealth.chathealth.entity.BaseEntity;
import chathealth.chathealth.entity.BoardComment;
import chathealth.chathealth.entity.BoardHit;
import chathealth.chathealth.entity.PictureBoard;
import chathealth.chathealth.entity.member.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
@SuperBuilder
@SQLDelete(sql = "UPDATE board SET deleted_date = CURRENT_TIMESTAMP where board_id = ?")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "board_id")
    private Long id;

    @Column(length = 2000)
    private String title;
    @Lob
    private String content;

    //    private Integer report;   //고민 좀 해보고..
    private LocalDateTime deletedDate;
    @Enumerated(STRING)
    private Category category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Users user;

    @OneToMany(mappedBy = "board")
    private final List<PictureBoard> pictureBoardList = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private final List<BoardHit> boardHitList = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private final List<BoardComment> boardCommentList = new ArrayList<>();

    public void update(BoardEditDto boardEditDto) {
        if (boardEditDto.getTitle() != null) this.title = boardEditDto.getTitle();
        if (boardEditDto.getContent() != null) this.content = boardEditDto.getContent();
        if (boardEditDto.getCategory() != null) this.category = boardEditDto.getCategory();
    }
}