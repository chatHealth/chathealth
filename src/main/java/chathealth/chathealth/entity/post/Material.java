package chathealth.chathealth.entity.post;

import chathealth.chathealth.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import static lombok.AccessLevel.PROTECTED;

@Entity
@ToString
@Setter
@Getter
@SuperBuilder

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = PROTECTED)
@DynamicUpdate
@Table(name = "Material")
public abstract class Material extends BaseEntity {
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

}