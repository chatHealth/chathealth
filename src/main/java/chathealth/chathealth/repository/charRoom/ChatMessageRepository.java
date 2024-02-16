package chathealth.chathealth.repository.charRoom;

import chathealth.chathealth.entity.ChatMessage;
import chathealth.chathealth.entity.chatRoom.ChatRoom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    Slice<ChatMessage> findAllByChatRoomOrderByTimestampDesc(ChatRoom chatRoom, Pageable pageable);

    Long countByChatRoom(ChatRoom chatRoom);
}