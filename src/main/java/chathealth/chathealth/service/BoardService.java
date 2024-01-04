package chathealth.chathealth.service;

import chathealth.chathealth.dto.request.BoardCreateDto;
import chathealth.chathealth.dto.request.BoardSearchDto;
import chathealth.chathealth.dto.response.BoardResponse;
import chathealth.chathealth.entity.borad.Board;
import chathealth.chathealth.entity.member.Users;
import chathealth.chathealth.exception.BoardNotFoundException;
import chathealth.chathealth.exception.UserNotFound;
import chathealth.chathealth.repository.MemberRepository;
import chathealth.chathealth.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    //게시글 생성
    public Board createBoard(BoardCreateDto boardCreateDto, Long id) {

        Board board = Board.builder()
                .title(boardCreateDto.getTitle())
                .content(boardCreateDto.getContent())
                .category(boardCreateDto.getCategory())
                .user((Users) memberRepository.findById(id).orElseThrow(UserNotFound::new))
                .build();

        return boardRepository.save(board);
    }

    //게시글 수정

    //게시글 삭제 soft delete

    //게시글 목록 조회
    public List<BoardResponse> getBoards(BoardSearchDto boardSearchDto){

        log.info("page==={}", boardSearchDto.getPage());
        log.info("size==={}", boardSearchDto.getSize());
        log.info("offset==={}", boardSearchDto.getOffset());

        return boardRepository.getBoards(boardSearchDto).stream().map(board -> BoardResponse.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .createdDate(board.getCreatedDate())
                .memberId(board.getUser().getId())
                .nickname(board.getUser().getNickname())
                .grade(board.getUser().getGrade())
                .commentCount(board.getBoardCommentList().size())
                .hit(board.getBoardHitList().size())
                .build())
                .collect(Collectors.toList());
    }

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
