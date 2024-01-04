package chathealth.chathealth.entity.post;

import chathealth.chathealth.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@Table(name = "Symptom")
public class Symptom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "symptom_id")
    private Long id;

    private String symptomName;

    // N:M conenct
    @OneToMany(mappedBy = "symptom")
    private List<SymptomPost> symptomList;

}