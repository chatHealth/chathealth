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
import org.springframework.data.domain.*;
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

    public Slice<ChatMessageResponse> getChatMessages(Long roomId, CustomUserDetails userDetails, Pageable pageable) {

        String email = userDetails.getLoggedMember().getEmail();
        Member member = memberRepository.findByEmail(email).orElseThrow(UserNotFound::new);

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(RoomNotFound::new);

        Slice<ChatMessage> messageSlices = chatMessageRepository.findAllByChatRoomOrderByTimestampDesc(chatRoom, pageable);

        List<ChatMessageResponse> list = messageSlices.getContent()
                .stream()
                .map(chatMessage ->{
                        // 내꺼인지
                    boolean isMine = chatMessage.getSender().getMember().equals(member);

                    return ChatMessageResponse.builder()
                            .message(chatMessage.getMessage())
                            .senderId(chatMessage.getSender().getId())
                            .nickname(chatMessage.getSender().getChatNickname())
                            .timestamp(chatMessage.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                            .isMine(isMine)
                            .build();})
                .toList();

        return new SliceImpl<>(list, pageable, messageSlices.hasNext());
    }
    @Transactional
    public ChatMessageResponse sendChatMessage(ChatMessageDto messageDto, String senderEmail) {

        ChatRoom chatRoom = chatRoomRepository.findById(messageDto.getRoomId()).orElseThrow(RoomNotFound::new);
        Member member = memberRepository.findByEmail(senderEmail).orElseThrow(UserNotFound::new);
        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findByChatRoomAndMemberAndDeletedDateIsNull(chatRoom, member).orElseThrow(UserNotFound::new);

        if (messageDto.getType().equals(ChatMessageType.ENTER)) {
            messageDto.setContent(messageDto.getNickname() + ENTER_MESSAGE.getMessage());
        } else if (messageDto.getType().equals(ChatMessageType.QUIT)) {
            messageDto.setContent(chatRoomMember.getChatNickname() + QUIT_MESSAGE.getMessage());
        }

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

    public Page<ChatRoomResponse> getChatRooms(Principal principal, Pageable pageable) {

        Member member = memberRepository.findByEmail(principal.getName()).orElseThrow(UserNotFound::new);

        Page<ChatRoom> chatRoomPage = chatRoomRepository.findAllByOrderByCreatedDateDesc(pageable);

        List<ChatRoomResponse> list = chatRoomPage.getContent().stream().map(chatRoom -> {
            boolean isJoined = chatRoomMemberRepository.existsByChatRoomAndMemberAndDeletedDateIsNull(chatRoom, member);

            return ChatRoomResponse.builder()
                    .id(chatRoom.getId())
                    .name(chatRoom.getName())
                    .image(chatRoom.getRoomImage())
                    .description(chatRoom.getDescription())
                    .userCount(chatRoom.getMembers())
                    .isJoined(isJoined)
                    .build();
        }).toList();

        return new PageImpl<>(list, pageable, chatRoomPage.getTotalElements());
    }

    public ChatRoomInner getChatRoom(Long id, CustomUserDetails userDetails) {

        String email = userDetails.getLoggedMember().getEmail();
        Member member = memberRepository.findByEmail(email).orElseThrow(UserNotFound::new);
        ChatRoom chatRoom = chatRoomRepository.findByIdFetch(id).orElseThrow(RoomNotFound::new);
        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findByChatRoomAndMemberAndDeletedDateIsNull(chatRoom, member).orElseThrow(UserNotFound::new);

        return ChatRoomInner.builder()
                .id(chatRoom.getId())
                .senderId(chatRoomMember.getId())
                .userCount(chatRoom.getMembers())
                .image(chatRoom.getRoomImage())
                .name(chatRoom.getName())
                .isRepresentative(chatRoom.getRepresentativeMember().equals(member))
                .description(chatRoom.getDescription())
                .build();
    }
    //채팅방 나가기
    @Transactional
    public void quitChatRoom(Long roomId, String memberEmail) {

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(RoomNotFound::new);
        Member member = memberRepository.findByEmail(memberEmail).orElseThrow(UserNotFound::new);

        chatRoomMemberRepository.quitChatRoomMember(chatRoom, member);
    }

    //채팅박 삭제
    @Transactional
    public void deleteChatRoom(Long roomId) {
        chatRoomRepository.deleteById(roomId);
    }
}
