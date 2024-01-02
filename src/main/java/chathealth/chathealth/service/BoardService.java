package chathealth.chathealth.service;

import chathealth.chathealth.dto.response.BoardResponse;
import chathealth.chathealth.entity.borad.Board;
import chathealth.chathealth.exception.BoardNotFoundException;
import chathealth.chathealth.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    //게시글 생성

    //게시글 수정

    //게시글 삭제 soft delete

    //게시글 목록 조회

    //게시글 조회
    public BoardResponse getBoard(long id) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);

        // 삭제된 게시물은 조회 불가
        if(board.getDeletedDate() != null){
            throw new BoardNotFoundException();
        }

        return BoardResponse.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .category(board.getCategory())
                .createdDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .memberId(board.getUser().getId())
                .nickname(board.getUser().getNickname())
                .profile(board.getUser().getProfile())
                .grade(board.getUser().getGrade())
                .commentCount(board.getBoardCommentList().size())
                .hit(board.getBoardHitList().size())
                .build();
    }
}
