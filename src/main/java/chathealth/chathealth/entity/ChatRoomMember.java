package chathealth.chathealth.entity;

import chathealth.chathealth.entity.chatRoom.ChatRoom;
import chathealth.chathealth.entity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomMember {

    @Id
    @Column(name = "room_member_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    @Column(name = "chat_nickname")
    private String chatNickname;

    private LocalDateTime deletedDate;

    @Builder
    public ChatRoomMember(Member member, ChatRoom chatRoom, String chatNickname) {
        this.member = member;
        this.chatRoom = chatRoom;
        this.chatNickname = chatNickname;
    }

    public static ChatRoomMember enterChatRoomMember(String chatNickname, ChatRoom chatRoom, Member member) {
        return ChatRoomMember.builder()
                        .chatNickname(chatNickname)
                        .chatRoom(chatRoom)
                        .member(member)
                        .build();
    }
}