package chathealth.chathealth.repository.board;

import chathealth.chathealth.dto.request.BoardSearchDto;
import chathealth.chathealth.entity.board.Board;

import java.util.List;

public interface BoardRepositoryCustom {

    List<Board> getBoards(BoardSearchDto boardSearchDto);
}
