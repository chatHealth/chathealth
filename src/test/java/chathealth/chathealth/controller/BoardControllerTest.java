package chathealth.chathealth.controller;

import chathealth.chathealth.entity.borad.Board;
import chathealth.chathealth.entity.member.Users;
import chathealth.chathealth.repository.MemberRepository;
import chathealth.chathealth.repository.board.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static chathealth.chathealth.entity.borad.Category.FREE;
import static chathealth.chathealth.entity.member.Grade.BLACK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BoardControllerTest {

    @Autowired
    BoardController boardController;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("게시물 1개 조회")
    public void getBoard() throws Exception{
        //given
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
        //expected
        mockMvc.perform(get("/board/{id}", board.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.boardId").value(board.getId()))
                .andExpect(jsonPath("$.title").value("제목입니다."))
                .andExpect(jsonPath("$.content").value("내용입니다."))
                .andExpect(jsonPath("$.category").value(FREE.toString()))
                .andExpect(jsonPath("$.createdDate").value(board.getCreatedDate().toString()))
                .andExpect(jsonPath("$.modifiedDate").value(board.getModifiedDate().toString()))
                .andExpect(jsonPath("$.memberId").value(board.getUser().getId()))
                .andExpect(jsonPath("$.nickname").value("장공오일"))
                .andExpect(jsonPath("$.profile").value("profilePicture"))
                .andExpect(jsonPath("$.grade").value(BLACK.toString()))
                .andExpect(jsonPath("$.commentCount").value(board.getBoardCommentList().size()))
                .andExpect(jsonPath("$.hit").value(board.getBoardHitList().size()));
    }
}