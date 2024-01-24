package chathealth.chathealth.controller;

import chathealth.chathealth.dto.response.ChatRoomInner;
import chathealth.chathealth.dto.response.ChatRoomResponse;
import chathealth.chathealth.dto.response.CustomUserDetails;
import chathealth.chathealth.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;

    // 채팅방 목록
    @GetMapping("/chat")
    public String chat(Model model, Principal principal) {
        List<ChatRoomResponse> chatRooms = chatService.getChatRooms(principal);
        model.addAttribute("chatRooms", chatRooms);

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

    @ResponseBody
    @PostMapping("/chat/room/{id}")
    public Long enterRoom(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @PathVariable Long id,
                            @RequestParam String nickname) {
        Long senderId = chatService.enterChatRoom(id, userDetails.getLoggedMember().getEmail(), nickname);

        return senderId;
    }
}
