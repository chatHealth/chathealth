package chathealth.chathealth.controller;

import chathealth.chathealth.dto.request.ChatMessageDto;
import chathealth.chathealth.dto.request.CreateChatRoom;
import chathealth.chathealth.dto.response.ChatMessageResponse;
import chathealth.chathealth.dto.response.member.CustomUserDetails;
import chathealth.chathealth.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatService chatService;
    private final SimpMessageSendingOperations messagingTemplate;
    private final Validator validator;

    @MessageMapping("/chat/message")
    public void message(ChatMessageDto messageDto, Principal principal) {
        System.out.println("messageDto = " + messageDto);
        Errors errors = new BeanPropertyBindingResult(messageDto, "messageDto");
        System.out.println("errors = " + errors);
        validator.validate(messageDto, errors);
        System.out.println("validator = " + validator);

        if (errors.hasErrors()) {
            log.error("validation error : {}", errors);
            return;
        }

        log.info("chatMessage : {}", messageDto);
        ChatMessageResponse response = chatService.sendChatMessage(messageDto, principal.getName());
        messagingTemplate.convertAndSend("/sub/chat/" + messageDto.getRoomId(), response);
    }

    @PostMapping("/chat/room")
    public Long createChat(@Valid @RequestPart CreateChatRoom createChatRoom,
                     @RequestPart(required = false) MultipartFile image,
                     @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long chatRoomId = chatService.createChatRoom(userDetails, createChatRoom, image);
        return chatRoomId;
    }
}
