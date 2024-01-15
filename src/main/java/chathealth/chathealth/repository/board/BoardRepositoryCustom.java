package chathealth.chathealth.repository.board;

import chathealth.chathealth.dto.request.BoardSearchDto;
import chathealth.chathealth.entity.board.Board;
import chathealth.chathealth.entity.board.Category;

import java.util.List;

public interface BoardRepositoryCustom {

    List<Board> getBoards(BoardSearchDto boardSearchDto);

    List<Board> getBoardsByCategoryRecent(Category category);
}
