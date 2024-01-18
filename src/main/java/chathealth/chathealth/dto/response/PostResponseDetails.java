package chathealth.chathealth.dto.response;


import chathealth.chathealth.entity.post.Material;
import chathealth.chathealth.entity.post.MaterialPost;
import chathealth.chathealth.entity.post.PicturePost;
import chathealth.chathealth.entity.post.SymptomType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PostResponseDetails {
    private Long id;
    private String title;
    private String content;

    private List<String> picturePost;

    private String company;

    private List<String> material;

    // yyyy-MM-dd 변환
    private String createdAt;

    private Integer hitCount;
    private Integer likeCount;
    private Integer reviewCount;


    public static List<String> extractMaterialNames(List<MaterialPost> materialPosts) {
        return materialPosts.stream()
                .map(materialPost -> materialPost.getMaterial().getMaterialName())
                .collect(Collectors.toList());
    }


}
