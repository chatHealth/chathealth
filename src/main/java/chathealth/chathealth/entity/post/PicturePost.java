package chathealth.chathealth.entity.post;

import jakarta.persistence.*;

import java.util.List;

//테이블명 = picture_post
@Entity
public class PicturePost {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "picture_id")
    private Long id;
    @Column(name = "picture_url")
    private String picture;
    private Integer orders;

    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;

}
