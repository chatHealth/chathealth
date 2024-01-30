package chathealth.chathealth.controller;

import chathealth.chathealth.dto.request.BoardCommentRequest;
import chathealth.chathealth.dto.response.BoardCommentResponse;
import chathealth.chathealth.dto.response.member.CustomUserDetails;
import chathealth.chathealth.service.BoardCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardCommentRestController {

    private final BoardCommentService boardCommentService;

    @GetMapping("/board-comment/{id}")
    public Page<BoardCommentResponse> getComments(@PathVariable("id") Long boardId, Pageable pageable, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        return boardCommentService.getComments(boardId, customUserDetails, pageable);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/board-comment/{id}")
    public BoardCommentResponse createComment(@PathVariable("id") Long boardId,
                                              @Valid @RequestBody BoardCommentRequest request,
                                              @AuthenticationPrincipal CustomUserDetails writer) {


       return boardCommentService.createComment(boardId, writer, request.getContent());
    }

    @PreAuthorize("@boardCommentService.isOwner(authentication, #commentId) || hasRole('ROLE_ADMIN')")
    @DeleteMapping("/board-comment/{commentId}")
    public void deleteComment(@PathVariable Long commentId){
        boardCommentService.deleteComment(commentId);
    }
}
