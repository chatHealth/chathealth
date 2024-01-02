package chathealth.chathealth.entity;

import chathealth.chathealth.entity.borad.Board;
import chathealth.chathealth.entity.member.Member;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
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

//    private Integer report;   //고민 좀 해보고..

    private LocalDateTime deletedDate;

    @OneToMany(mappedBy = "boardComment")
    private List<Likes> likeList = new ArrayList<>();

}