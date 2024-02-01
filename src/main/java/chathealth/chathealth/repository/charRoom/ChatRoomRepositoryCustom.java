package chathealth.chathealth.repository.charRoom;

import chathealth.chathealth.constants.ChatSearchCondition;
import chathealth.chathealth.entity.chatRoom.ChatRoom;
import chathealth.chathealth.entity.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ChatRoomRepositoryCustom {

    Page<ChatRoom> getChatRooms(Pageable pageable, Member member, ChatSearchCondition condition);

    Optional<ChatRoom> findByIdFetch(Long id);

}
