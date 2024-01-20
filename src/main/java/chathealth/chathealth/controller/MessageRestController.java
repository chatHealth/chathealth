package chathealth.chathealth.controller;

import chathealth.chathealth.dto.request.SendMessageDto;
import chathealth.chathealth.dto.response.CustomUserDetails;
import chathealth.chathealth.dto.response.message.MessageReceiveResponse;
import chathealth.chathealth.dto.response.message.MessageReceiveResponseDetail;
import chathealth.chathealth.dto.response.message.MessageSendResponse;
import chathealth.chathealth.dto.response.message.MessageSendResponseDetail;
import chathealth.chathealth.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MessageRestController {

    private final MessageService messageService;

    //쪽지 보내기
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/api/message/{id}")
    public void sendMessage(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                            @PathVariable Long id,
                            @Valid @RequestBody SendMessageDto sendMessageDto) {

        messageService.sendMessage(customUserDetails, id, sendMessageDto);
    }

    //받은 쪽지함
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/api/message/received")
    public Page<MessageReceiveResponse> receivedMessages(@AuthenticationPrincipal CustomUserDetails customUserDetails, Pageable pageable) {
        return messageService.receivedMessages(customUserDetails, pageable);
    }

    //보낸 쪽지함
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/api/message/send")
    public Page<MessageSendResponse> sendMessages(@AuthenticationPrincipal CustomUserDetails customUserDetails, Pageable pageable) {
        return messageService.sendMessages(customUserDetails, pageable);
    }

    //쪽지 삭제(보낸 사람 쪽에서만 삭제)
    @PreAuthorize("hasRole('ROLE_ADMIN') || @messageService.isSender(authentication, #id)")
    @DeleteMapping("/api/message/{id}")
    public void deleteSendMessage(@PathVariable Long id) {
        messageService.deleteSendMessage(id);
    }

    //보낸 쪽지 읽기
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/api/message/send/{id}")
    public MessageSendResponseDetail getSendMessage(@PathVariable Long id) {
        return messageService.getSendMessage(id);
    }

    //받은 쪽지 읽기
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/api/message/received/{id}")
    public MessageReceiveResponseDetail getReceivedMessage(@PathVariable Long id) {
        return messageService.getReceivedMessage(id);
    }

    //읽지 않은 쪽지 개수
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/api/message/unread")
    public Long getUnreadMessageCount(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return messageService.getUnreadMessageCount(customUserDetails);
    }

    //읽지 않은 쪽지함
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/api/message/received/unread")
    public Page<MessageReceiveResponse> getUnreadMessages(@AuthenticationPrincipal CustomUserDetails customUserDetails, Pageable pageable) {
        return messageService.getUnreadMessages(customUserDetails, pageable);
    }
}