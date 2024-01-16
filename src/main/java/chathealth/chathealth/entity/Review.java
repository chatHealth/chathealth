package chathealth.chathealth.entity;

import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.entity.post.Post;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Lob
    private String content;

    private double score;

    private LocalDateTime deletedDate;

    @OneToMany(mappedBy = "review")
    private List<ReComment> reComments = new ArrayList<>();

    @OneToMany(mappedBy = "review")
    private List<Helpful> helpfulList = new ArrayList<>();
}
