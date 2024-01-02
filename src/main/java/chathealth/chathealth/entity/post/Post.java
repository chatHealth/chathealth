package chathealth.chathealth.entity.post;

import chathealth.chathealth.entity.BaseEntity;
import chathealth.chathealth.entity.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
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
    private Member member;

    @Column(length = 400)
    private String title;

    @Column(length = 3000)
    private String content;


    private Integer report;

    private LocalDateTime deletedDate;


    // connect
    @OneToMany(mappedBy = "post")
    private List<PicturePost> postImgList;

    @OneToMany(mappedBy = "post")
    private List<PostHit> postHitList;

    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikeList;


    // N:M conenct
    @OneToMany(mappedBy = "post")
    private List<SymptomPost> symptomList;

    @OneToMany(mappedBy = "post")
    private List<MaterialPost> materialList;



    // provide setter method
//    public void update(String content) {
//        this.content = content;
//    }

}
