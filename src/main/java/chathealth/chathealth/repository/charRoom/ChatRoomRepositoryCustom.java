package chathealth.chathealth.repository.charRoom;

import chathealth.chathealth.constants.ChatSearchCondition;
import chathealth.chathealth.entity.chatRoom.ChatRoom;
import chathealth.chathealth.entity.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepositoryCustom {

//    Page<ChatRoom> findAllByOrderByCreatedDateDesc(Pageable pageable);

    Page<ChatRoom> getChatRooms(Pageable pageable, Member member, ChatSearchCondition condition);

    List<Long> joinedChatRoomIds(Member member);

    Optional<ChatRoom> findByIdFetch(Long id);

}
