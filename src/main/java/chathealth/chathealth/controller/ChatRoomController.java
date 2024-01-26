package chathealth.chathealth.controller;

import chathealth.chathealth.constants.ChatSearchCondition;
import chathealth.chathealth.dto.request.ChatRoomEntrance;
import chathealth.chathealth.dto.response.ChatMessageResponse;
import chathealth.chathealth.dto.response.ChatRoomInner;
import chathealth.chathealth.dto.response.ChatRoomResponse;
import chathealth.chathealth.dto.response.CustomUserDetails;
import chathealth.chathealth.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;

    // 채팅방 목록
    @GetMapping("/chat")
    public String chat(Model model, Principal principal, Pageable pageable, ChatSearchCondition condition) {
        Page<ChatRoomResponse> chatRooms = chatService.getChatRooms(principal, pageable, condition);
        model.addAttribute("chatRooms", chatRooms);
        model.addAttribute("condition", condition);

        return "chat/chat";
    }

    // 채팅방
    @GetMapping("/chat/{id}")
    public String chatRoom(@AuthenticationPrincipal CustomUserDetails userDetails,
                           @PathVariable Long id, Model model) {
        ChatRoomInner chatRoom = chatService.getChatRoom(id, userDetails);
        model.addAttribute("room", chatRoom);
        return "chat/room";
    }

    //채팅방 기존 메세지(slice)
    @PreAuthorize("permitAll()")
    @ResponseBody
    @GetMapping("/chat/room/{id}")
    public Slice<ChatMessageResponse> getChatMessages(@PathVariable Long id, Pageable pageable,
                                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        Slice<ChatMessageResponse> chatMessages = chatService.getChatMessages(id, userDetails, pageable);
        return chatMessages;
    }

    //채팅방 입장
    @ResponseBody
    @PostMapping("/chat/room/enter")
    public Long enterRoom(@AuthenticationPrincipal CustomUserDetails userDetails,
                          @Valid @RequestBody ChatRoomEntrance chatRoomEntrance) {
        Long senderId = chatService.enterChatRoom(chatRoomEntrance.getRoomId(), userDetails.getLoggedMember().getEmail(), chatRoomEntrance.getNickname());

        return senderId;
    }

    //채팅방 퇴장
    @ResponseBody
    @DeleteMapping("/chat/room/{id}")
    public void quitRoom(@AuthenticationPrincipal CustomUserDetails userDetails,
                         @PathVariable Long id) {

        chatService.quitChatRoom(id, userDetails.getLoggedMember().getEmail());
    }

    //채팅방 삭제
    @ResponseBody
    @DeleteMapping("/chat/{id}")
    public void deleteRoom(@PathVariable Long id) {
        chatService.deleteChatRoom(id);
    }
}
