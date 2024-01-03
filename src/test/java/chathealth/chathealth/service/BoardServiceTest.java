package chathealth.chathealth.service;

import chathealth.chathealth.dto.request.BoardSearchDto;
import chathealth.chathealth.dto.response.BoardResponse;
import chathealth.chathealth.entity.borad.Board;
import chathealth.chathealth.entity.member.Users;
import chathealth.chathealth.repository.MemberRepository;
import chathealth.chathealth.repository.board.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static chathealth.chathealth.entity.borad.Category.FREE;
import static chathealth.chathealth.entity.member.Grade.BLACK;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("게시글 조회")
    public void getBoard() throws Exception {
//        given
        Users user = Users.builder()
                .nickname("장공오일")
                .grade(BLACK)
                .profile("profilePicture")
                .build();
        memberRepository.save(user);
//
        Board board = Board.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .user(user)
                .category(FREE)
                .build();
        boardRepository.save(board);
//        when
        BoardResponse findBoard = boardService.getBoard(board.getId());
//        then
        assertThat(findBoard.getBoardId()).isEqualTo(board.getId());
        assertThat(findBoard.getTitle()).isEqualTo("제목입니다.");
        assertThat(findBoard.getContent()).isEqualTo("내용입니다.");
        assertThat(findBoard.getCategory()).isEqualTo(FREE);
        assertThat(findBoard.getCreatedDate()).isEqualTo(board.getCreatedDate());
        assertThat(findBoard.getModifiedDate()).isEqualTo(board.getModifiedDate());
        assertThat(findBoard.getMemberId()).isEqualTo(board.getUser().getId());
        assertThat(findBoard.getNickname()).isEqualTo(board.getUser().getNickname());
        assertThat(findBoard.getProfile()).isEqualTo(board.getUser().getProfile());
        assertThat(findBoard.getGrade()).isEqualTo(board.getUser().getGrade());
        assertThat(findBoard.getCommentCount()).isEqualTo(board.getBoardCommentList().size());
        assertThat(findBoard.getHit()).isEqualTo(board.getBoardHitList().size());
    }

    @Test
    @DisplayName("게시글 목록 조회")
    public void getBoards() throws Exception {
        //given
        Users user = Users.builder()
                .nickname("장공오일")
                .grade(BLACK)
                .profile("profilePicture")
                .build();
        memberRepository.save(user);
//
        List<Board> boardList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Board board = Board.builder()
                    .title("제목입니다.")
                    .content("내용입니다.")
                    .user(user)
                    .category(FREE)
                    .build();
            boardList.add(board);
        }
            boardRepository.saveAll(boardList);

        BoardSearchDto boardSearchDto = BoardSearchDto.builder()
                .category(FREE)
                .build();

        //when
        List<BoardResponse> boards = boardService.getBoards(boardSearchDto);
        //then
        assertThat(boards.size()).isEqualTo(20);
    }
}