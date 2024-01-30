package chathealth.chathealth.repository.charRoom;

import chathealth.chathealth.entity.chatRoom.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {

}
