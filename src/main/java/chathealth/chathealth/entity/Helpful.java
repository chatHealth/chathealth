package chathealth.chathealth.entity;

import chathealth.chathealth.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
public class Helpful {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "helpful_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}