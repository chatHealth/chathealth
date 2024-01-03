package chathealth.chathealth.entity;

import chathealth.chathealth.entity.borad.Board;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class PictureBoard {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "postimg_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "picture_url", length = 2000)
    private String url;
}