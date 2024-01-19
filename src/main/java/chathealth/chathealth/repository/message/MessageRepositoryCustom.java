package chathealth.chathealth.repository.message;

import chathealth.chathealth.dto.response.message.MessageReceiveResponse;
import chathealth.chathealth.dto.response.message.MessageSendResponse;
import chathealth.chathealth.entity.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageRepositoryCustom {


    //받은 쪽지함
    Page<MessageReceiveResponse> receivedMessages(Member me, Pageable pageable);

    //보낸 쪽지함
    Page<MessageSendResponse> sendMessages(Member me, Pageable pageable);

    Page<MessageReceiveResponse> getUnreadMessages(Member member, Pageable pageable);
}
