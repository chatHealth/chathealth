package chathealth.chathealth.entity.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PROTECTED;


@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@Table(name = "PicturePost")
public class PicturePost {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "picture_id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "picture_url",length = 2000)
    private String pictureUrl;

    private Integer orders;

}
