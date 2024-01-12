package chathealth.chathealth.controller;

import chathealth.chathealth.constants.Role;
import chathealth.chathealth.dto.response.CustomUserDetails;
import chathealth.chathealth.entity.member.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
public class IndexController {

    @GetMapping("/")
    public String index(Model model,@AuthenticationPrincipal CustomUserDetails customUserDetails){
        Boolean isAuth = customUserDetails !=null; //로그인 됨.
        if(isAuth){
            model.addAttribute("loggedMember",customUserDetails.getLoggedMember());
        }
        return "index"; }
}
