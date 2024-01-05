package chathealth.chathealth.repository.board;

import chathealth.chathealth.dto.request.BoardSearchDto;
import chathealth.chathealth.entity.board.Board;
import chathealth.chathealth.entity.board.Category;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static chathealth.chathealth.entity.board.QBoard.board;
import static chathealth.chathealth.entity.member.QUsers.users;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Board> getBoards(BoardSearchDto boardSearchDto){
        return queryFactory.selectFrom(board)
                .where(categoryEq(boardSearchDto.getCategory()),
                        titleContains(boardSearchDto.getTitle()),
                        contentContains(boardSearchDto.getContent()),
                        writerContains(boardSearchDto.getWriter()))
                .join(board.user, users)
                .fetchJoin()
                .limit(boardSearchDto.getSize())
                .offset(boardSearchDto.getOffset())
                .orderBy(board.createdDate.desc())
                .fetch();
    }

    private BooleanExpression categoryEq(Category category) {
        return category == null ? null : board.category.eq(category);
    }

    private static BooleanExpression contentContains(String content) {
        return hasText(content) ? board.title.contains(content) : null;
    }
    private static BooleanExpression writerContains(String writer) {
        return hasText(writer) ? board.content.contains(writer) : null;
    }
    private static BooleanExpression titleContains(String title) {
        return hasText(title) ? users.nickname.contains(title) : null;
    }
}
