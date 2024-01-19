package chathealth.chathealth.repository.boardComment;

import chathealth.chathealth.dto.response.BoardCommentResponse;
import chathealth.chathealth.dto.response.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardCommentRepositoryCustom {

    Page<BoardCommentResponse> findAllByBoardId(Long boardId, CustomUserDetails customUserDetails, Pageable pageable);
}
