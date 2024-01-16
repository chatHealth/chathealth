package chathealth.chathealth.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialDto {
    private Long id;
    private String materialName;
    private String functions;
}
