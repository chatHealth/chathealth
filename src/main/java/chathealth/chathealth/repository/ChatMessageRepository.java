package chathealth.chathealth.repository;

import chathealth.chathealth.entity.ChatMessage;
import chathealth.chathealth.entity.chatRoom.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByChatRoom(ChatRoom chatRoom);


}