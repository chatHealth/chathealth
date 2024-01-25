package chathealth.chathealth.repository;

import chathealth.chathealth.entity.chatRoom.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ChatRoomRepositoryCustom {

    Page<ChatRoom> findAllByOrderByCreatedDateDesc(Pageable pageable);

    Optional<ChatRoom> findByIdFetch(Long id);

}
