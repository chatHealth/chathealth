package chathealth.chathealth.entity.post;

import chathealth.chathealth.entity.BaseEntity;
import chathealth.chathealth.entity.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public abstract class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_id")
    private Long id;
    private String title;
    private String content;
    private LocalDateTime deletedDate;


    @JoinColumn(name = "member_id")
    @ManyToOne()
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<PostHit> postHitList;

    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikeList;

    @OneToMany(mappedBy = "post")
    private List<PicturePost> picturePostList;

}
