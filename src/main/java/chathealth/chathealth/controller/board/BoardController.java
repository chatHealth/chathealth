package chathealth.chathealth.controller.board;

import chathealth.chathealth.dto.request.BoardCreateDto;
import chathealth.chathealth.dto.request.BoardSearchDto;
import chathealth.chathealth.dto.response.BoardResponse;
import chathealth.chathealth.dto.response.member.CustomUserDetails;
import chathealth.chathealth.exception.NotPermitted;
import chathealth.chathealth.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static chathealth.chathealth.constants.Role.ROLE_ADMIN;
import static chathealth.chathealth.constants.Role.ROLE_USER;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/board")
    public String getBoards(BoardSearchDto boardSearchDto, Model model) {
        model.addAttribute("boards", boardService.getBoards(boardSearchDto));
        model.addAttribute("pageResponse", boardService.getBoardPage(boardSearchDto));
        return "board/boards";
    }

    @GetMapping("/board/write")
    public String writeForm(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if(customUserDetails == null || !List.of(ROLE_USER, ROLE_ADMIN).contains(customUserDetails.getLoggedMember().getRole())){
            throw new NotPermitted();
        }
        model.addAttribute("boardCreateDto", new BoardCreateDto());
        return "board/write";
    }

    @GetMapping("/board/{id}")
    public String getBoard(@PathVariable long id, Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        model.addAttribute("board", boardService.getBoard(id, customUserDetails));
        return "board/board";
    }

    @GetMapping("/board/{id}/edit")
    public String editForm(@PathVariable long id, Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        // 자신이 작성한 글만 수정 가능
        BoardResponse board = boardService.getBoard(id, customUserDetails);
        if (!board.getIsWriter()) {
            throw new NotPermitted("작성자만 수정할 수 있습니다.");
        }
        model.addAttribute("board", board);
        return "board/edit";
    }
}