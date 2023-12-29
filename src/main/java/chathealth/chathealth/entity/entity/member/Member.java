package chathealth.chathealth.entity.entity.member;

import chathealth.chathealth.entity.BaseEntity;
import chathealth.chathealth.entity.member.Address;
import chathealth.chathealth.entity.member.Role;
import chathealth.chathealth.entity.post.Post;
import chathealth.chathealth.entity.post.PostHit;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String pw;
    private LocalDate birth;
    @Embedded
    private Address address;
    @Column(length = 1000)
    private String profile;
    private Role role;
    private int report;
    private LocalDateTime deletedDate;


    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
    private List<Post> postList;

    @OneToMany(mappedBy = "member")
    private List<PostHit> postHitList;
}
