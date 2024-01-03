package chathealth.chathealth.entity;

import chathealth.chathealth.entity.borad.Board;
import chathealth.chathealth.entity.member.Member;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class BoardHit {

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
}