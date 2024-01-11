package chathealth.chathealth.service;

import chathealth.chathealth.dto.request.BoardCreateDto;
import chathealth.chathealth.dto.request.BoardEditDto;
import chathealth.chathealth.dto.request.BoardSearchDto;
import chathealth.chathealth.dto.response.BoardResponse;
import chathealth.chathealth.entity.board.Board;
import chathealth.chathealth.entity.member.Users;
import chathealth.chathealth.exception.BoardNotFoundException;
import chathealth.chathealth.repository.MemberRepository;
import chathealth.chathealth.repository.board.BoardRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static chathealth.chathealth.entity.board.Category.FREE;
import static chathealth.chathealth.constants.Grade.BLACK;
import static chathealth.chathealth.constants.Role.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager em;

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

    @Test
    @DisplayName("게시글 생성")
//    @Rollback(value = false)
    public void createBoard() throws Exception {
        //given
        Users user = Users.builder()
                .nickname("장공오일")
                .grade(BLACK)
                .profile("profilePicture")
                .role(USER)
                .build();
        memberRepository.save(user);

        BoardCreateDto board = BoardCreateDto.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .category(FREE)
                .build();
        //when
        Board savedBoard = boardService.createBoard(board, user.getId());

        //then
        BoardResponse findBoard = boardService.getBoard(savedBoard.getId());
        assertThat(findBoard.getBoardId()).isEqualTo(savedBoard.getId());
        assertThat(findBoard.getTitle()).isEqualTo("제목입니다.");
        assertThat(findBoard.getContent()).isEqualTo("내용입니다.");
        assertThat(findBoard.getCategory()).isEqualTo(FREE);
        assertThat(findBoard.getCreatedDate()).isEqualTo(savedBoard.getCreatedDate());
        assertThat(findBoard.getModifiedDate()).isEqualTo(savedBoard.getModifiedDate());
        assertThat(findBoard.getMemberId()).isEqualTo(user.getId());
        assertThat(findBoard.getNickname()).isEqualTo("장공오일");
    }

    @Test
    @DisplayName("게시글 수정")
    public void updateBoard() throws Exception {
        //given
        Users user = Users.builder()
                .nickname("장공오일")
                .grade(BLACK)
                .profile("profilePicture")
                .role(USER)
                .build();
        memberRepository.save(user);

        BoardCreateDto boardCreateDto = BoardCreateDto.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .category(FREE)
                .build();

        Board savedBoard = boardService.createBoard(boardCreateDto, user.getId());

        BoardEditDto boardEditDto = BoardEditDto.builder()
                .title("제목ㅋㅋㅋㅋㅋㅋㅋㅋ")
                .content("내용ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ")
                .category(FREE)
                .build();

        boardRepository.findById(savedBoard.getId()).orElseThrow(BoardNotFoundException::new);
        //when
        boardService.updateBoard(boardEditDto, user.getId(), savedBoard.getId());

        em.flush();
        em.clear();

        Board board = boardRepository.findById(savedBoard.getId()).orElseThrow(BoardNotFoundException::new);
        //then
        assertThat(board.getTitle()).isEqualTo("제목ㅋㅋㅋㅋㅋㅋㅋㅋ");
        assertThat(board.getContent()).isEqualTo("내용ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ");
    }

    @Test
    @DisplayName("게시글 삭제")
//    @Rollback(value = false)
    public void deleteBoard() throws Exception {
        //given
        Users user = Users.builder()
                .nickname("장공오일")
                .grade(BLACK)
                .profile("profilePicture")
                .role(USER)
                .build();
        memberRepository.save(user);

        BoardCreateDto board = BoardCreateDto.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .category(FREE)
                .build();
        Board savedBoard = boardService.createBoard(board, user.getId());
        //when
        boardService.deleteBoard(user.getId(), savedBoard.getId());

        //then
        assertThrows(BoardNotFoundException.class, () -> boardService.getBoard(savedBoard.getId()));
    }
}