package chathealth.chathealth.entity.post;

import chathealth.chathealth.dto.request.PostWriteDto;
import chathealth.chathealth.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@Table(name = "MaterialPost")
public class MaterialPost{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "mp_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "material_id")
    private Material material;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;


}