package chathealth.chathealth.entity.post;

import chathealth.chathealth.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PROTECTED;
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@Table(name = "SymptomPost")
public class SymptomPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "sp_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "symptom_id")
    private Symptom symptom;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}