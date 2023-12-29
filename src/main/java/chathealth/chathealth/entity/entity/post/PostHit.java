package chathealth.chathealth.entity.entity.post;

import chathealth.chathealth.entity.member.Member;
import jakarta.persistence.*;

@Entity
@Table(name = "PostHit")
public class PostHit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "hit_id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne()
    @JoinColumn(name = "member_id")
    private Member member;
}
