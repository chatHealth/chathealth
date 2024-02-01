package chathealth.chathealth.dto.response;


import chathealth.chathealth.entity.post.MaterialPost;
import lombok.*;

import java.time.LocalDateTime;
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
    private Long memberId;
    private String title;
    private String content;

    private List<String> picturePost;

    private String company;

    private List<String> material;
    private List<String> materialInfo;

    private LocalDateTime createdAt;

    private Integer hitCount;
    private Long likeCount;
    private Integer reviewCount;
    private double score;

    public static List<String> extractMaterialNames(List<MaterialPost> materialPosts) {
        return materialPosts.stream()
                .map(materialPost -> materialPost.getMaterial().getMaterialName())
                .collect(Collectors.toList());
    }
    public static List<String> extractMaterialInfo(List<MaterialPost> materialPosts) {
        return materialPosts.stream()
                .map(materialPost -> materialPost.getMaterial().getFunctions())
                .collect(Collectors.toList());
    }

}
