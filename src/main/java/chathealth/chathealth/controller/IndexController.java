package chathealth.chathealth.controller;
import chathealth.chathealth.constants.Role;
import chathealth.chathealth.dto.response.CustomUserDetails;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

        if (customUserDetails != null){
            Member loggedMember = customUserDetails.getLoggedMember();
            String email = loggedMember.getEmail();
            Role role = loggedMember.getRole();

            if (Role.getEntRoles().contains(role)) {
                model.addAttribute("member", memberService.getEntInfo(email));
            } else {
                memberService.getUserInfo(email);
                model.addAttribute("member", memberService.getUserInfo(email));
            }
        }
        return "index";
    }
}