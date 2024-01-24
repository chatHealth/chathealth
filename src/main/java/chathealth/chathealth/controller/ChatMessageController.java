package chathealth.chathealth.controller;

import chathealth.chathealth.dto.request.ChatMessageDto;
import chathealth.chathealth.dto.request.CreateChatRoom;
import chathealth.chathealth.dto.response.ChatMessageResponse;
import chathealth.chathealth.dto.response.CustomUserDetails;
import chathealth.chathealth.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatService chatService;
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat/message")
    public void message(ChatMessageDto messageDto, Principal principal) {

        ChatMessageResponse response = chatService.sendChatMessage(messageDto, principal.getName());
        messagingTemplate.convertAndSend("/sub/chat/" + messageDto.getRoomId(), response);
    }

    @PostMapping("/chat/room")
    public Long chat(@RequestPart CreateChatRoom createChatRoom,
                     @RequestPart MultipartFile image,
                     @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long chatRoomId = chatService.createChatRoom(userDetails, createChatRoom, image);
        return chatRoomId;
    }
}
