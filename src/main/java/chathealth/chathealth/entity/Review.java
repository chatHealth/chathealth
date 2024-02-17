package chathealth.chathealth.entity;

import chathealth.chathealth.dto.request.ReviewModDto;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.entity.post.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE review SET deleted_date = CURRENT_TIMESTAMP where review_id = ?")
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

    @OneToMany(mappedBy = "review")
    private List<PictureReView> pictureReList=new ArrayList<>();


    public void update(ReviewModDto reviewModDto) {
        this.content = reviewModDto.getContent();
        this.score = reviewModDto.getScore();
    }
    public void updateDelete(){

            this.deletedDate = LocalDateTime.now();

    }
}
