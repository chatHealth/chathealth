package chathealth.chathealth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;


@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@Table(name = "PictureReView")
public class PictureReView {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "picture_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(name = "picture_url",length = 2000)
    private String pictureUrl;


}
