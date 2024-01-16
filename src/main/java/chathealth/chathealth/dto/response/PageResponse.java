package chathealth.chathealth.dto.response;

import chathealth.chathealth.entity.board.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PageResponse {

    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int size;
    private int startPage;
    private int endPage;

    private String title;
    private String content;
    private Category category;

    @Builder
    public PageResponse(long totalElements, int totalPages, int currentPage, int size, String title, String content, Category category) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.size = size;
        this.startPage = getStartPage();
        this.endPage = getEndPage();
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public int getStartPage() {
        int group = currentPage / 10; // 현재 페이지 그룹을 찾습니다.
        return group * 10 + 1;
    }

    public int getEndPage() {
        int group = currentPage / 10;
        int endPage = (group + 1) * 10;
        return Math.min(endPage, totalPages);
    }
}