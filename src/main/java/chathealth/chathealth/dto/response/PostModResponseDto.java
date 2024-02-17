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
public class PostModResponseDto {
    private Long id;
    private Long memberId;
    private String title;
    private String content;

    private String picturePostMain;
    private List<String> picturePostSer;

    private String company;

    private Long symptom;
    private List<Long> materialId;

    private LocalDateTime createdAt;

    private Integer hitCount;
    private Long likeCount;
    private Integer reviewCount;

    public static List<String> extractMaterialNames(List<MaterialPost> materialPosts) {
        return materialPosts.stream()
                .map(materialPost -> materialPost.getMaterial().getMaterialName())
                .collect(Collectors.toList());
    }



}
