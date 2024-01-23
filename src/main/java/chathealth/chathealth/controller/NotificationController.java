package chathealth.chathealth.controller;

import chathealth.chathealth.exception.ExpiredSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/noty")
public class NotificationController {

    private final Map<String, SseEmitter> clients = new ConcurrentHashMap<>();

    @GetMapping("/subscribe")
    public SseEmitter subscribe(@RequestParam String id, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if(session == null) {
            throw new ExpiredSession("세션이 만료되었습니다. 재로그인이 필요합니다.");
        }

        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        clients.put(id, sseEmitter);

        sseEmitter.onCompletion(() -> clients.remove(id));
        sseEmitter.onTimeout(() -> clients.remove(id));

        return sseEmitter;
    }

    public void dispatchNewMessage(String id, String message) {
        SseEmitter sseEmitter = clients.get(id);
        if (sseEmitter != null) {
            try {
                JSONObject jsonMessage = new JSONObject();
                jsonMessage.put("message", message);
                sseEmitter.send(SseEmitter.event().data(jsonMessage));
            } catch (Exception e) {
                sseEmitter.completeWithError(e);
                clients.remove(id);
            }
        }
    }

}
