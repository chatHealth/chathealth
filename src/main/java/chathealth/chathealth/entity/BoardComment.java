package chathealth.chathealth.entity;

import chathealth.chathealth.entity.board.Board;
import chathealth.chathealth.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
@SuperBuilder
public class BoardComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "boardcomment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 2000)
    private String content;


    private LocalDateTime deletedDate;

    @OneToMany(mappedBy = "boardComment")
    private List<Likes> likeList = new ArrayList<>();

}