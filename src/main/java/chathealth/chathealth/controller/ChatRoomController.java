package chathealth.chathealth.controller;

import chathealth.chathealth.dto.response.ChatRoomResponse;
import chathealth.chathealth.dto.response.CustomUserDetails;
import chathealth.chathealth.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        model.addAttribute("room", chatService.getChatRoom(id, userDetails));
        return "chat/room";
    }

    // 채팅방 가입
    @PostMapping("/chat/{id}")
    public String joinChatRoom(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @PathVariable Long id) {
//        chatService.joinChatRoom(id, userDetails);
        return "redirect:/chat/" + id;
    }
}
