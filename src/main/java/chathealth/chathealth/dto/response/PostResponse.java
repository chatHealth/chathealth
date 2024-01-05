package chathealth.chathealth.dto.response;

import chathealth.chathealth.entity.post.Material;
import chathealth.chathealth.entity.post.PicturePost;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@ToString
public class PostResponse {

    private Long id;
    private String title;
    private String content;

    private List<PicturePost> picturePost;
    private String representativeImg;

    private String company;

    private String symptom;
    private List<Material> material;

    private LocalDateTime createdDate;
}