package chathealth.chathealth.entity;

import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.entity.post.Post;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "question_id")
    private Long id;

    private String title;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Lob
    private String content;

    private LocalDateTime deletedDate;

    @OneToOne(mappedBy = "question", fetch = LAZY)
    private Answer answer;
}