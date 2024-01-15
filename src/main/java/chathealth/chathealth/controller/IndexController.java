package chathealth.chathealth.controller;



import chathealth.chathealth.dto.response.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import chathealth.chathealth.entity.member.Ent;
import chathealth.chathealth.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class IndexController {

    private final MemberService memberService;

    @GetMapping("/")
    public String index(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        boolean isAuth = customUserDetails != null;
        if (isAuth){
            String email = customUserDetails.getLoggedMember().getEmail();
            if (customUserDetails.getLoggedMember() instanceof Ent) {
                model.addAttribute("member", memberService.getEntInfo(email));
            } else {
                memberService.getUserInfo(email);
                model.addAttribute("member", memberService.getUserInfo(email));
            }
        }
        model.addAttribute("isAuth", isAuth);
        return "index";
    }
}
