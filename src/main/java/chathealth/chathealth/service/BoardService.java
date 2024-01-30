package chathealth.chathealth.service;

import chathealth.chathealth.constants.Role;
import chathealth.chathealth.dto.request.BoardCreateDto;
import chathealth.chathealth.dto.request.BoardEditDto;
import chathealth.chathealth.dto.request.BoardSearchDto;
import chathealth.chathealth.dto.response.BoardResponse;
import chathealth.chathealth.dto.response.member.CustomUserDetails;
import chathealth.chathealth.dto.response.PageResponse;
import chathealth.chathealth.entity.BoardHit;
import chathealth.chathealth.entity.board.Board;
import chathealth.chathealth.entity.board.Category;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.entity.member.Users;
import chathealth.chathealth.exception.BoardNotFoundException;
import chathealth.chathealth.exception.NotPermitted;
import chathealth.chathealth.exception.UserNotFound;
import chathealth.chathealth.repository.boardHit.BoardHitRepository;
import chathealth.chathealth.repository.MemberRepository;
import chathealth.chathealth.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static chathealth.chathealth.constants.Role.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardHitRepository boardHitRepository;

    //게시글 생성
    public Board createBoard(BoardCreateDto boardCreateDto, Long id) {

        Member member = memberRepository.findById(id).orElseThrow(UserNotFound::new);
        List<Role> entRoles = List.of(ROLE_WAITING_ENT, ROLE_PERMITTED_ENT, ROLE_REJECTED_ENT);
        if (entRoles.contains(member.getRole())) {
            throw new NotPermitted("사업자는 게시글을 작성할 수 없습니다.");
        }

        if (member.getRole().equals(ROLE_USER) && boardCreateDto.getCategory().equals(Category.NOTICE)) {
            throw new NotPermitted();
        }

        Board board = Board.builder()
                .title(boardCreateDto.getTitle())
                .content(boardCreateDto.getContent())
                .category(boardCreateDto.getCategory())
                .user((Users) member)
                .build();

        return boardRepository.save(board);
    }

    //게시글 수정
    @Transactional
    public void updateBoard(BoardEditDto boardEditDto, Member member, Long boardId) {

        Board findBoard = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        if (!findBoard.getUser().getEmail().equals(member.getEmail()) && !member.getRole().equals(ROLE_ADMIN)) {
            throw new NotPermitted();
        }

        findBoard.update(boardEditDto);
    }

    //게시글 삭제 soft delete

    public void deleteBoard(Long boardId, Member member) {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        if (!findBoard.getUser().getEmail().equals(member.getEmail()) && !member.getRole().equals(ROLE_ADMIN)) {
            throw new NotPermitted();
        }

        boardRepository.delete(findBoard);
    }

    //게시글 목록 조회
    public List<BoardResponse> getBoards(BoardSearchDto boardSearchDto) {

        long totalPage = (long) Math.ceil(getBoardCount(boardSearchDto) / (double) boardSearchDto.getSize());
        if (boardSearchDto.getPage() > totalPage - 1) {
            boardSearchDto.setPage((int) totalPage - 1);
        }

        return boardRepository.getBoards(boardSearchDto).stream().map(board -> BoardResponse.builder()
                        .boardId(board.getId())
                        .title(board.getTitle())
                        .createdDate(board.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                        .memberId(board.getUser().getId())
                        .nickname(board.getUser().getNickname())
                        .grade(board.getUser().getGrade())
                        .commentCount(board.getBoardCommentList().size())
                        .hit(board.getBoardHitList().size())
                        .name(board.getUser().getName())
                        .category(board.getCategory())
                        .build())
                .collect(Collectors.toList());
    }

    public List<BoardResponse> getRecentBoards(Category category) {
        return boardRepository.getBoardsByCategoryRecent(category).stream().map(board -> BoardResponse.builder()
                        .boardId(board.getId())
                        .title(board.getTitle())
                        .createdDate(board.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                        .hit(board.getBoardHitList().size())
                        .category(board.getCategory())
                        .build())
                .toList();
    }

    //게시글 조회
    public BoardResponse getBoard(long id, CustomUserDetails customUserDetails) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);

        // 삭제된 게시물은 조회 불가
        if (board.getDeletedDate() != null) {
            throw new BoardNotFoundException("삭제된 게시물입니다.");
        }

        String email = (customUserDetails == null) ? null : customUserDetails.getLoggedMember().getEmail();
        Role role = customUserDetails != null ? customUserDetails.getLoggedMember().getRole() : null;

        // 로그인한 유저가 12시간 내 조회하지 않았으면 조회수 증가
        if (customUserDetails != null) {
            Member findMember = memberRepository.findByEmail(email).orElseThrow(UserNotFound::new);
            if (!boardHitRepository.existsHit(board, findMember)) {
                boardHitRepository.save(new BoardHit(board, findMember));
            }
        }

        Users user = board.getUser();
        return BoardResponse.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .category(board.getCategory())
                .createdDate(board.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .modifiedDate(board.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .memberId(user.getId())
                .nickname(user.getNickname())
                .name(user.getName())
                .profile(user.getProfile())
                .grade(user.getGrade())
                .commentCount(board.getBoardCommentList().size())
                .hit(board.getBoardHitList().size())
                .isWriter(user.getEmail().equals(email) || (role != null && role.equals(ROLE_ADMIN)))
                .build();
    }

    public Long getBoardCount(BoardSearchDto boardSearchDto) {
        return boardRepository.getBoardCount(boardSearchDto);
    }

    public PageResponse getBoardPage(BoardSearchDto boardSearchDto) {
        Long boardCount = getBoardCount(boardSearchDto);
        return PageResponse.builder()
                .totalPages((int) Math.ceil(boardCount / (double) boardSearchDto.getSize()))
                .totalElements(boardCount)
                .currentPage(boardSearchDto.getPage())
                .size(boardSearchDto.getSize())
                .title(boardSearchDto.getTitle())
                .content(boardSearchDto.getContent())
                .category(boardSearchDto.getCategory())
                .build();
    }
}
