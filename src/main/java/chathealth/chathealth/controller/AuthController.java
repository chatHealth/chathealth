package chathealth.chathealth.controller;

import chathealth.chathealth.constants.Role;
import chathealth.chathealth.dto.request.member.EntJoinDto;
import chathealth.chathealth.dto.request.member.UserJoinDto;
import chathealth.chathealth.dto.response.member.CustomUserDetails;
import chathealth.chathealth.entity.member.Address;
import chathealth.chathealth.service.AuthService;
import chathealth.chathealth.service.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static chathealth.chathealth.constants.Role.ROLE_USER;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final MailService mailService;

    //로그인
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "auth/login";
    }

    //@Secured("hasAnyRole('ROLE_USER','ROLE_WAITING_ENT','ROLE_PERMITTED_ENT','ROLE_REJECTED_ENT','ROLE_ADMIN')")
    @PostMapping("/login")
    public String loginProcess() {
        return "redirect:/";
    }

    //로그아웃
    @PostMapping("/logout")
    public String logout() {
        return "auth/logout";
    }

    //가입
    @GetMapping("/selection") //개인회원가입창 진입
    public String selection(Model model) {
        return "auth/selection";
    }

    @GetMapping("/userjoin") //개인회원가입창 진입
    public String userJoin(Model model) {
        return "auth/user-join";
    }

    @PostMapping("/userjoin") //개인회원가입 처리
    public String userJoin(@ModelAttribute UserJoinDto userJoinDto) {
        //주소 객체화
        Address addressEntity = Address.builder()
                .postcode(userJoinDto.getPostcode())
                .address(userJoinDto.getFrontAddress())
                .addressDetail(userJoinDto.getAddressDetail())
                .build();
        //service에 던질 DTO 빌드
        userJoinDto.setAddress(addressEntity);
        authService.userJoin(userJoinDto);

        return "redirect:/auth/login";
    }

    @GetMapping("/entjoin") //사업자회원가입창 진입
    public String entJoin(Model model) {
        return "auth/ent-join";
    }

    @PostMapping("/entjoin") //사업자회원가입 처리
    public String entJoin(@ModelAttribute EntJoinDto entJoinDto) {
        //주소 객체화
        Address addressEntity = Address.builder()
                .postcode(entJoinDto.getPostcode())
                .address(entJoinDto.getFrontAddress())
                .addressDetail(entJoinDto.getAddressDetail())
                .build();

        //service에 던질 DTO 빌드
        entJoinDto.setAddress(addressEntity);

        authService.entJoin(entJoinDto);
        return "redirect:/auth/ent-join";
    }

    @Transactional
    @ResponseBody
    @DeleteMapping("/withdraw/{id}")  //탈퇴
    public Map<String,Integer> memberWithdraw(@PathVariable Long id,String email){
        Integer result = authService.memberWithdraw(id);
        Map<String,Integer> resultmap = new HashMap<>();
        resultmap.put("isDone",result);
        try {
            mailService.sendWithdraw(email);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return resultmap;
    }

    @PostMapping("/confirmEmail") //아이디 중복체크
    @ResponseBody
    public Map<String, Boolean> idCheck(@RequestParam("email") String email) {
        boolean isExist = authService.confirmEmail(email);
        Map<String, Boolean> resultMap = new HashMap<>();
        resultMap.put("isExist", isExist);
        return resultMap;

    }

    // 로그인 상태 확인 api
    @ResponseBody
    @GetMapping("/login-check")
    public Boolean loginCheck(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return customUserDetails != null;
    }

    // 로그인한게 유저인지 확인 api
    @ResponseBody
    @GetMapping("/is-user")
    public Boolean isUser(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if(customUserDetails == null) {
            return false;
        }
        return customUserDetails.getLoggedMember().getRole().equals(ROLE_USER);
    }

    // 로그인한게 ent인지 확인 api
    @ResponseBody
    @GetMapping("/is-ent")
    public Boolean isEnt(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if(customUserDetails == null) {
            return false;
        }
        return Role.getEntRoles().contains(customUserDetails.getLoggedMember().getRole());
    }

    // 로그인한 유저 email 식별자
    @ResponseBody
    @GetMapping("/email")
    public String email(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if(customUserDetails == null || Role.getEntRoles().contains(customUserDetails.getLoggedMember().getRole())){
            return null;
        }
        return customUserDetails.getLoggedMember().getEmail();
    }

}