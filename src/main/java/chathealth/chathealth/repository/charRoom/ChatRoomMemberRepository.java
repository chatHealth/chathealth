package chathealth.chathealth.repository.charRoom;

import chathealth.chathealth.entity.ChatRoomMember;
import chathealth.chathealth.entity.chatRoom.ChatRoom;
import chathealth.chathealth.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {

    public Optional<ChatRoomMember> findByChatRoomAndMemberAndDeletedDateIsNull(ChatRoom chatRoom, Member member);

    public boolean existsByChatRoomAndMemberAndDeletedDateIsNull(ChatRoom chatRoom, Member member);

    @Modifying
    @Query("UPDATE ChatRoomMember crm SET crm.deletedDate = CURRENT_TIMESTAMP WHERE crm.chatRoom = :chatRoom AND crm.member = :member")
    public void quitChatRoomMember(ChatRoom chatRoom, Member member);
}
