package chathealth.chathealth.entity;

import chathealth.chathealth.entity.board.Board;
import chathealth.chathealth.entity.member.Member;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(indexes = {@Index(name="idx_member", columnList = "member_id"),
        @Index(name="idx_board", columnList = "board_id")})
public class BoardHit extends BaseEntity{

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "hit_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public BoardHit(Board board, Member member) {
        this.board = board;
        this.member = member;
    }
}