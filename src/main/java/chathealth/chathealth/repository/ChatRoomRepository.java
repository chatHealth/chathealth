package chathealth.chathealth.repository;

import chathealth.chathealth.entity.chatRoom.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Page<ChatRoom> findAllByOrderByCreatedDateDesc(Pageable pageable);
}
