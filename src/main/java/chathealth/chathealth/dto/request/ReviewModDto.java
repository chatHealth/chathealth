package chathealth.chathealth.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReviewModDto {
    private String content;
    private double score;
    private List<String> pictureReList;
}
