package chathealth.chathealth.entity;

import chathealth.chathealth.dto.request.ChatMessageType;
import chathealth.chathealth.entity.chatRoom.ChatRoom;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "chat_message_id")
    private Long id;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private ChatRoomMember sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private ChatMessageType type;


    @Builder
    public ChatMessage(String message, ChatRoomMember sender, ChatRoom chatRoom, ChatMessageType type) {
        this.message = message;
        this.sender = sender;
        this.chatRoom = chatRoom;
        this.timestamp = LocalDateTime.now();
        this.type = type;
    }
}
