package chathealth.chathealth.controller;

import chathealth.chathealth.dto.request.UserJoinDto;
import chathealth.chathealth.entity.member.Address;
import chathealth.chathealth.entity.member.Grade;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

 private final AuthService authService;

    @GetMapping("/userjoin") //개인회원가입창 진입
    public String userJoin(Model model) {
        //model.addAttribute("UserJoinDto", new UserJoinDto());
        return "auth/userjoin";
    }

    @PostMapping("/userjoin") //개인회원가입 처리
    public String userJoin(@ModelAttribute UserJoinDto userJoinDto) {
        Address addressEntity = Address.builder()
                .postcode(userJoinDto.getPostcode())
                .address(userJoinDto.getFrontAddress())
                .addressDetail(userJoinDto.getAddressDetail())
        .build();

        UserJoinDto insertUserDto = UserJoinDto.builder()
                .email(userJoinDto.getEmail())
                .address(addressEntity)
                .pw(userJoinDto.getPw())
                .name(userJoinDto.getName())
                .nickname(userJoinDto.getNickname())
                .birth(userJoinDto.getBirth())
                .profile(userJoinDto.getProfile())
                .role(userJoinDto.getRole())
                .grade(Grade.BRONZE)
                .report(0)
                .build();
        log.info(String.valueOf(insertUserDto));
        authService.join(insertUserDto);
        return "redirect:/auth/join";
    }


    @PostMapping("/confirmEmail") //아이디 중복체크
    @ResponseBody
    public Map<String,Integer> idCheck(@RequestParam("email") String email) {
        int count = authService.confirmEmail(email);
        Map<String,Integer> resultMap = new HashMap<>();
        resultMap.put("isCount",count);
        return resultMap;

    }
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginProcess(){
        return "redirect:/";
    }
}
