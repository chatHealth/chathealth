package chathealth.chathealth.entity.post;

import chathealth.chathealth.entity.member.Member;
import jakarta.persistence.*;

@Entity
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne()
    @JoinColumn(name = "member_id")
    private Member member;
}
