package chathealth.chathealth.entity.post;

import chathealth.chathealth.entity.BaseEntity;
import chathealth.chathealth.entity.Review;
import chathealth.chathealth.entity.member.Ent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@ToString
@Getter
@SuperBuilder

@NoArgsConstructor(access = PROTECTED)
@DynamicUpdate

@Table(name = "Post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "member_id")
    private Ent member;

    @Column(length = 400)
    private String title;

    @Column(length = 3000)
    private String content;

    private LocalDateTime deletedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "symptom_id")
    private Symptom symptom;


    // connect
    @OneToMany(mappedBy = "post")
    private List<PicturePost> postImgList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostHit> postHitList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikeList = new ArrayList<>();


    // N:M conenct
    @OneToMany(mappedBy = "post")
    private List<MaterialPost> materialList;

    @OneToMany(mappedBy = "post")
    private List<Review> reviewList = new ArrayList<>();


    public Long getPostHitCount() {
        if (postHitList == null) return 0L;
        return (long) postHitList.size();
    }

    public Long getPostLikeCount() {
        if(postLikeList == null) return 0L;
        return (long) postLikeList.size();
    }

    public Long getReviewCount() {
        if(reviewList == null) return 0L;
        return (long) reviewList.size();
    }
}