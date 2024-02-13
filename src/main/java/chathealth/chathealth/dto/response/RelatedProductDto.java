package chathealth.chathealth.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatedProductDto {
    private Long postId;
    private String postImg;
    private String title;
    private Integer postLike;
}
