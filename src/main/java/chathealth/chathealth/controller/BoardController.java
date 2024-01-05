package chathealth.chathealth.controller;

import chathealth.chathealth.dto.request.BoardCreateDto;
import chathealth.chathealth.dto.request.BoardEditDto;
import chathealth.chathealth.dto.request.BoardSearchDto;
import chathealth.chathealth.dto.response.BoardResponse;
import chathealth.chathealth.dto.response.CustomUserDetails;
import chathealth.chathealth.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @ResponseBody
    @GetMapping("/board/{id}")
    public BoardResponse getBoard(@PathVariable long id) {
        return boardService.getBoard(id);
    }

    @ResponseBody
    @GetMapping("/board")
    public List<BoardResponse> getBoards(BoardSearchDto boardSearchDto) {
        return boardService.getBoards(boardSearchDto);
    }

    @PostMapping("/board")
    @ResponseBody
    public void createBoard(@RequestBody @Valid BoardCreateDto boardCreateDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        boardService.createBoard(boardCreateDto, customUserDetails.getLoggedMember().getId());
    }

    @PatchMapping("/board/{id}")
    @ResponseBody
    public void updateBoard(@PathVariable Long id, BoardEditDto boardEditDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        boardService.updateBoard(boardEditDto, customUserDetails.getLoggedMember().getId(), id);
    }

    @DeleteMapping("/board/{id}")
    @ResponseBody
    public void deleteBoard(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        boardService.deleteBoard(id, customUserDetails.getLoggedMember().getId());
    }
}
