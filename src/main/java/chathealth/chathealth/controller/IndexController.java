package chathealth.chathealth.controller;

import chathealth.chathealth.constants.Role;
import chathealth.chathealth.dto.response.member.CustomUserDetails;
import chathealth.chathealth.dto.response.member.EntIndexDto;
import chathealth.chathealth.dto.response.member.UserIndexDto;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.service.MemberService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final MemberService memberService;

    @GetMapping("/")
    public String index(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {

        if (customUserDetails != null){
            Member loggedMember = customUserDetails.getLoggedMember();
            Long id = loggedMember.getId();
            Role role = loggedMember.getRole();

            if (Role.getEntRoles().contains(role)) {
                EntIndexDto entForIndex = memberService.getEntForIndex(id);
                model.addAttribute("member", entForIndex);
            } else {
                UserIndexDto userInfoForIndex = memberService.getUserInfoForIndex(id);
                model.addAttribute("member", userInfoForIndex);
            }
        }
        return "index";
    }
}