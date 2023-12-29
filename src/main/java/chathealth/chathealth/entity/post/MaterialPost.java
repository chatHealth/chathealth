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
@Table(name = "MaterialPost")

public abstract class MaterialPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "mp_id")
    private Long id;

    @ManyToMany()
    @JoinColumn(name = "material_id")
    private Material MaterialId;

    @ManyToMany()
    @JoinColumn(name = "post_id")
    private Post postId;


}