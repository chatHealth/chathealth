package chathealth.chathealth.controller;

import chathealth.chathealth.dto.request.BoardCreateDto;
import chathealth.chathealth.dto.request.BoardSearchDto;
import chathealth.chathealth.dto.response.CustomUserDetails;
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
            return "redirect:/login";
        }
        model.addAttribute("boardCreateDto", new BoardCreateDto());
        return "board/write";
    }

    @GetMapping("/board/{id}")
    public String getBoard(@PathVariable long id, Model model) {
        model.addAttribute("board", boardService.getBoard(id));
        return "board/board";
    }
}