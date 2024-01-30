package chathealth.chathealth.controller.board;

import chathealth.chathealth.dto.request.BoardCreateDto;
import chathealth.chathealth.dto.request.BoardEditDto;
import chathealth.chathealth.dto.response.BoardResponse;
import chathealth.chathealth.dto.response.member.CustomUserDetails;
import chathealth.chathealth.entity.board.Category;
import chathealth.chathealth.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardService boardService;


    @PostMapping(value = "/board")
    public Long createBoard(@RequestBody @Valid BoardCreateDto boardCreateDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return boardService.createBoard(boardCreateDto, customUserDetails.getLoggedMember().getId()).getId();
    }

    @PatchMapping("/board/{id}")
    public void updateBoard(@PathVariable Long id,@RequestBody @Valid BoardEditDto boardEditDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        boardService.updateBoard(boardEditDto, customUserDetails.getLoggedMember(), id);
    }

    @DeleteMapping("/board/{id}")
    public void deleteBoard(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        boardService.deleteBoard(id, customUserDetails.getLoggedMember());
    }

    @GetMapping("/board/api/recent")
    public List<BoardResponse> getRecentBoards(Category category) {
        return boardService.getRecentBoards(category);
    }
}
