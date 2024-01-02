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
@Table(name = "Material")
public class Material extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "material_id")
    private Long id;

    private String materialName;

    private Integer orders;

    // api
    private Integer highLimit;
    private Integer lowLimit;
    private String unit;
    private String recognition;
    private String caution;
    private String fuctions;

    // N:M conenct
    @OneToMany(mappedBy = "material")
    private List<MaterialPost> materialList;
}