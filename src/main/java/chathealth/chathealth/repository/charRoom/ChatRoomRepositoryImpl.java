package chathealth.chathealth.repository.charRoom;

import chathealth.chathealth.constants.ChatSearchCondition;
import chathealth.chathealth.entity.chatRoom.ChatRoom;
import chathealth.chathealth.entity.chatRoom.QChatRoom;
import chathealth.chathealth.entity.member.Member;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static chathealth.chathealth.entity.QChatRoomMember.chatRoomMember;
import static chathealth.chathealth.entity.chatRoom.QChatRoom.chatRoom;

@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public Page<ChatRoom> getChatRooms(Pageable pageable, Member member, ChatSearchCondition condition) {

        BooleanBuilder whereClause = new BooleanBuilder(chatRoomMember.deletedDate.isNull());

        if (condition == ChatSearchCondition.JOINED) {
            whereClause.and(chatRoom.in(
                    JPAExpressions.select(chatRoomMember.chatRoom)
                            .from(chatRoomMember)
                            .where(chatRoomMember.member.eq(member)
                                    .and(chatRoomMember.deletedDate.isNull())))
            );

        }

        List<ChatRoom> chatRooms = queryFactory.selectFrom(chatRoom)
                .leftJoin(chatRoom.chatRoomMembers, chatRoomMember)
                .fetchJoin()
                .where(whereClause)
                .orderBy(chatRoom.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory.select(chatRoom.countDistinct())
                .from(chatRoom)
                .leftJoin(chatRoom.chatRoomMembers, chatRoomMember)
                .where(whereClause)
                .fetchOne();

        return new PageImpl<>(chatRooms, pageable, count == null ? 0 : count);
    }

    @Override
    public Optional<ChatRoom> findByIdFetch(Long id) {
        ChatRoom chatRoom = queryFactory.selectFrom(QChatRoom.chatRoom)
                .leftJoin(QChatRoom.chatRoom.chatRoomMembers, chatRoomMember)
                .where(chatRoomMember.deletedDate.isNull())
                .fetchJoin()
                .where(QChatRoom.chatRoom.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(chatRoom);
    }
}
