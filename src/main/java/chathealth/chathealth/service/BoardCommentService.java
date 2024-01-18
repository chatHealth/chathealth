package chathealth.chathealth.service;

import chathealth.chathealth.dto.response.BoardCommentResponse;
import chathealth.chathealth.dto.response.CustomUserDetails;
import chathealth.chathealth.entity.BoardComment;
import chathealth.chathealth.exception.BoardNotFoundException;
import chathealth.chathealth.exception.UserNotFound;
import chathealth.chathealth.repository.MemberRepository;
import chathealth.chathealth.repository.board.BoardRepository;
import chathealth.chathealth.repository.boardComment.BoardCommentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardCommentService {

    private final BoardCommentRepository boardCommentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    public BoardCommentResponse createComment(Long boardId, CustomUserDetails writer, String content) {

        content = StringEscapeUtils.escapeHtml4(content);

        BoardComment boardComment = BoardComment.builder()
                .board(boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new))
                .member(memberRepository.findByEmail(writer.getLoggedMember().getEmail()).orElseThrow(UserNotFound::new))
                .content(content).build();


        BoardComment savedComment = boardCommentRepository.save(boardComment);

        return BoardCommentResponse.builder()
                .id(savedComment.getId())
                .content(savedComment.getContent())
                .memberId(savedComment.getMember().getId())
                .createdDate(savedComment.getCreatedDate())
                .build();
    }

    public void deleteComment(Long commentId) {
        boardCommentRepository.deleteById(commentId);
    }

    public Page<BoardCommentResponse> getComments(Long boardId, CustomUserDetails customUserDetails,Pageable pageable) {
        return boardCommentRepository.findAllByBoardId(boardId, customUserDetails, pageable);
    }

    public boolean isOwner(Authentication authentication, Long commentId) {
        String currentUsername = authentication.getName();
        String writerEmail = boardCommentRepository.findById(commentId).orElseThrow(BoardNotFoundException::new).getMember().getEmail();

        return currentUsername.equals(writerEmail);
    }
}
