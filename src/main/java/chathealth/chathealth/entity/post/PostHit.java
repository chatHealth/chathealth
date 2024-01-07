package chathealth.chathealth.entity.post;

import chathealth.chathealth.entity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "PostHit")
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
