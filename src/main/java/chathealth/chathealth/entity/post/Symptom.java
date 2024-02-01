package chathealth.chathealth.entity.post;

import chathealth.chathealth.dto.request.PostWriteDto;
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
public class Symptom{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "symptom_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private SymptomType symptomName;

    @OneToMany(mappedBy = "symptom")
    private List<Post> postList;


    @OneToMany(mappedBy = "symptom")
    private List<Material> materialList;


}