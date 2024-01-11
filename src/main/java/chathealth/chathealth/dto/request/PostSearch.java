package chathealth.chathealth.dto.request;

import chathealth.chathealth.entity.post.SymptomType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Builder
@ToString
public class PostSearch {

    //검색
    private String title;
    private String company;

    //필터
    private SymptomType symptomType;
    private List<String> materialName;

    //정렬
    private OrderCondition ordercondition;

    private static final int MAX_SIZE = 200;

    @Builder.Default
    private Integer page = 0;
    @Builder.Default
    private Integer size = 20;

    public Integer getSize() {
        return size == null ? 20 : this.size;
    }

    public Integer getPage() {
        return page == null ? 0 : this.page;
    }

    public OrderCondition getOrdercondition() {
        return ordercondition == null ? OrderCondition.RECENT : this.ordercondition;
    }

    public void setPage(Integer page){
        this.page = page;
    }

    public long getOffset() {
        size = this.size == null ? 20 : this.size;
        page = this.page == null ? 0 : this.page;
        return (long) max(0, page) * min(MAX_SIZE, size);
    }
}