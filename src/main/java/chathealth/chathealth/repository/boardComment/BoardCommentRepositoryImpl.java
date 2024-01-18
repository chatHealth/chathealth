package chathealth.chathealth.repository.boardComment;

import chathealth.chathealth.dto.response.BoardCommentResponse;
import chathealth.chathealth.dto.response.CustomUserDetails;
import chathealth.chathealth.entity.BoardComment;
import chathealth.chathealth.entity.member.Users;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static chathealth.chathealth.entity.QBoardComment.boardComment;

@RequiredArgsConstructor
public class BoardCommentRepositoryImpl implements BoardCommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BoardCommentResponse> findAllByBoardId(Long boardId, CustomUserDetails customUserDetails, Pageable pageable) {
        List<BoardComment> fetch = queryFactory.selectFrom(boardComment)
                .where(boardComment.board.id.eq(boardId))
                .innerJoin(boardComment.member)
                .fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(boardComment.createdDate.asc())
                .fetch();

        String loggedEmail = customUserDetails == null ? null : customUserDetails.getLoggedMember().getEmail();

        List<BoardCommentResponse> list = fetch.stream()
                .filter(comment -> comment.getMember() instanceof Users)
                .map(comment -> {
                            Users user = (Users) comment.getMember();
                            return BoardCommentResponse.builder()
                                    .content(comment.getContent())
                                    .createdDate(comment.getCreatedDate())
                                    .id(comment.getId())
                                    .memberId(user.getId())
                                    .memberNickname(user.getNickname() == null? user.getName() : user.getNickname())
                                    .profile(comment.getMember().getProfile())
                                    .grade(user.getGrade())
                                    .isWriter(user.getEmail().equals(loggedEmail))
                                    .build();
                        }).toList();

        Long total = queryFactory.select(boardComment.count())
                .from(boardComment)
                .where(boardComment.board.id.eq(boardId))
                .fetchOne();

        return new PageImpl<>(list, pageable, total == null ? 0 : total);
    }
}
