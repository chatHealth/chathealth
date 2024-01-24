package chathealth.chathealth.repository;

import chathealth.chathealth.entity.ChatRoomMember;
import chathealth.chathealth.entity.chatRoom.ChatRoom;
import chathealth.chathealth.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {

    public Optional<ChatRoomMember> findByChatRoomAndMember(ChatRoom chatRoom, Member member);

    public boolean existsByChatRoomAndMember(ChatRoom chatRoom, Member member);
}
