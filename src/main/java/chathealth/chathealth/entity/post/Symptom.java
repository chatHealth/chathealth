package chathealth.chathealth.entity.post;

import chathealth.chathealth.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@ToString
@Getter
@SuperBuilder

@NoArgsConstructor(access = PROTECTED)
@DynamicUpdate
@Table(name = "Symptom")
public class Symptom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "symptom_id")
    private Long id;

    private String symptomName;

    private Integer orders;

    // N:M conenct
    @OneToMany(mappedBy = "symptom")
    private List<SymptomPost> symptomList;

}