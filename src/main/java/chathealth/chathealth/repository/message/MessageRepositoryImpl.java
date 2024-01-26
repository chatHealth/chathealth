package chathealth.chathealth.repository.message;

import chathealth.chathealth.dto.response.message.MessageReceiveResponse;
import chathealth.chathealth.dto.response.message.MessageSendResponse;
import chathealth.chathealth.entity.Message;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.entity.member.Users;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static chathealth.chathealth.entity.QMessage.message;

@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    //받은 쪽지함
    @Override
    public Page<MessageReceiveResponse> receivedMessages(Member me, Pageable pageable) {
        List<Message> fetch = queryFactory.selectFrom(message)
                .where(message.receiver.eq(me), message.deletedReceivedDate.isNull())
                .orderBy(message.createdDate.desc())
                .innerJoin(message.sender)
                .fetchJoin()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long count = queryFactory.select(message.count())
                .from(message)
                .where(message.receiver.eq(me))
                .fetchOne();

        return getMessageReceiveResponses(pageable, fetch, count);
    }

    //보낸 쪽지함
    @Override
    public Page<MessageSendResponse> sendMessages(Member me, Pageable pageable) {
        List<Message> fetch = queryFactory.selectFrom(message)
                .where(message.sender.eq(me),
                        message.deletedDate.isNull())
                .orderBy(message.createdDate.desc())
                .innerJoin(message.receiver)
                .fetchJoin()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long count = queryFactory.select(message.count())
                .from(message)
                .where(message.sender.eq(me))
                .fetchOne();

        List<MessageSendResponse> list = fetch.stream()
                .filter(message -> message.getReceiver() instanceof Users)
                .map(message -> {
                    Users receiver = (Users) message.getReceiver();
                    return MessageSendResponse.get(message, receiver);
                }).toList();

        return new PageImpl<>(list, pageable, count == null ? 0 : count);
    }

    @Override
    public Page<MessageReceiveResponse> getUnreadMessages(Member member, Pageable pageable) {
        List<Message> fetch = queryFactory.selectFrom(message)
                .where(message.receiver.eq(member), message.isRead.eq(0))
                .innerJoin(message.sender)
                .fetchJoin()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(message.createdDate.desc())
                .fetch();

        Long count = queryFactory.select(message.count())
                .from(message)
                .where(message.receiver.eq(member), message.isRead.eq(0))
                .fetchOne();
        
        return getMessageReceiveResponses(pageable, fetch, count);
    }

    private Page<MessageReceiveResponse> getMessageReceiveResponses(Pageable pageable, List<Message> fetch, Long count) {
        List<MessageReceiveResponse> list = fetch.stream()
                .filter(message -> message.getSender() instanceof Users)
                .map(message -> {
                    Users sender = (Users) message.getSender();
                    return MessageReceiveResponse.get(message, sender);
                }).toList();

        System.out.println("list.size() = " + list.size());

        return new PageImpl<>(list, pageable, count == null ? 0 : count);
    }
}