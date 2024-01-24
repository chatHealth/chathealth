package chathealth.chathealth.service;

import chathealth.chathealth.dto.request.ChatMessageDto;
import chathealth.chathealth.dto.request.ChatMessageType;
import chathealth.chathealth.dto.request.CreateChatRoom;
import chathealth.chathealth.dto.response.ChatMessageResponse;
import chathealth.chathealth.dto.response.ChatRoomInner;
import chathealth.chathealth.dto.response.ChatRoomResponse;
import chathealth.chathealth.dto.response.CustomUserDetails;
import chathealth.chathealth.entity.ChatMessage;
import chathealth.chathealth.entity.ChatRoomMember;
import chathealth.chathealth.entity.chatRoom.ChatRoom;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.exception.RoomNotFound;
import chathealth.chathealth.exception.UserNotFound;
import chathealth.chathealth.repository.ChatMessageRepository;
import chathealth.chathealth.repository.ChatRoomMemberRepository;
import chathealth.chathealth.repository.ChatRoomRepository;
import chathealth.chathealth.repository.MemberRepository;
import chathealth.chathealth.util.ImageUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static chathealth.chathealth.constants.Constants.ENTER_MESSAGE;
import static chathealth.chathealth.constants.Constants.QUIT_MESSAGE;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final MemberRepository memberRepository;

    private final ImageUpload imageUpload;

    @Transactional
    public Long createChatRoom(CustomUserDetails userDetails, CreateChatRoom createChatRoom, MultipartFile image) {
        String email = userDetails.getLoggedMember().getEmail();
        Member member = memberRepository.findByEmail(email).orElseThrow(UserNotFound::new);

        String roomImage = imageUpload.uploadImage(image, "chat");
        ChatRoom chatRoom = ChatRoom.createChatRoom(createChatRoom, member, roomImage);

        ChatRoomMember chatRoomMember = ChatRoomMember.enterChatRoomMember(createChatRoom.getNickname(), chatRoom, member);

        chatRoomRepository.save(chatRoom);
        chatRoomMemberRepository.save(chatRoomMember);

        return chatRoom.getId();
    }

    public List<ChatMessageResponse> getChatMessages(Long roomId, CustomUserDetails userDetails) {

        String email = userDetails.getLoggedMember().getEmail();
        Member member = memberRepository.findByEmail(email).orElseThrow(UserNotFound::new);

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(RoomNotFound::new);

        return chatMessageRepository.findAllByChatRoom(chatRoom)
                .stream().map(chatMessage ->
                        ChatMessageResponse.builder()
                                .message(chatMessage.getMessage())
                                .senderId(chatMessage.getSender().getId())
                                .nickname(chatMessage.getSender().getChatNickname())
                                .timestamp(chatMessage.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                                .build()).toList();
    }
    @Transactional
    public ChatMessageResponse sendChatMessage(ChatMessageDto messageDto, String senderEmail) {
        if (messageDto.getType().equals(ChatMessageType.ENTER)) {
            messageDto.setContent(messageDto.getNickname() + ENTER_MESSAGE.getMessage());
        } else if (messageDto.getType().equals(ChatMessageType.QUIT)) {
            messageDto.setContent(messageDto.getNickname() + QUIT_MESSAGE.getMessage());
        }

        ChatRoom chatRoom = chatRoomRepository.findById(messageDto.getRoomId()).orElseThrow(RoomNotFound::new);
        Member member = memberRepository.findByEmail(senderEmail).orElseThrow(UserNotFound::new);
        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findByChatRoomAndMember(chatRoom, member).orElseThrow(UserNotFound::new);

        ChatMessage message = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(chatRoomMember)
                .message(messageDto.getContent())
                .type(messageDto.getType())
                .build();

        chatMessageRepository.save(message);


        return ChatMessageResponse.builder()
                .message(message.getMessage())
                .nickname(message.getSender().getChatNickname())
                .timestamp(message.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .senderId(messageDto.getSenderId())
                .build();

    }

    @Transactional
    public Long enterChatRoom(Long roomId, String memberEmail, String nickname) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(RoomNotFound::new);
        Member member = memberRepository.findByEmail(memberEmail).orElseThrow(UserNotFound::new);

        ChatRoomMember chatRoomMember = ChatRoomMember.enterChatRoomMember(nickname, chatRoom, member);

        ChatRoomMember savedRoomMember = chatRoomMemberRepository.save(chatRoomMember);

        return savedRoomMember.getId();
    }

    public void quitChatRoom(ChatMessageDto messageDto, String senderEmail) {
        ChatRoom chatRoom = chatRoomRepository.findById(messageDto.getRoomId()).orElseThrow(RoomNotFound::new);
        Member member = memberRepository.findByEmail(senderEmail).orElseThrow(UserNotFound::new);
        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findByChatRoomAndMember(chatRoom, member).orElseThrow(UserNotFound::new);

        chatRoomMemberRepository.delete(chatRoomMember);
    }

    public List<ChatRoomResponse> getChatRooms(Principal principal) {

        Member member = memberRepository.findByEmail(principal.getName()).orElseThrow(UserNotFound::new);

        List<ChatRoom> list = chatRoomRepository.findAll();

        return list.stream().map(chatRoom ->
                {
                    boolean isJoined = chatRoomMemberRepository.existsByChatRoomAndMember(chatRoom, member);

                    return ChatRoomResponse
                            .builder()
                            .id(chatRoom.getId())
                            .name(chatRoom.getName())
                            .image(chatRoom.getRoomImage())
                            .description(chatRoom.getDescription())
                            .userCount(chatRoom.getMembers())
                            .isJoined(isJoined)
                            .build();
                })
                .toList();
    }

    public ChatRoomInner getChatRoom(Long id, CustomUserDetails userDetails) {

        String email = userDetails.getLoggedMember().getEmail();
        Member member = memberRepository.findByEmail(email).orElseThrow(UserNotFound::new);
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow(RoomNotFound::new);
        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findByChatRoomAndMember(chatRoom, member).orElseThrow(UserNotFound::new);

        return ChatRoomInner.builder()
                .id(chatRoom.getId())
                .senderId(chatRoomMember.getId())
                .userCount(chatRoom.getMembers())
                .image(chatRoom.getRoomImage())
                .name(chatRoom.getName())
                .representativeMemberId(chatRoom.getRepresentativeMember().getId())
                .description(chatRoom.getDescription())
                .build();
    }
}
