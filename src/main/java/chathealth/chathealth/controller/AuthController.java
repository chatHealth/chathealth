package chathealth.chathealth.controller;

import chathealth.chathealth.dto.request.UserJoinDto;
import chathealth.chathealth.entity.member.Address;
import chathealth.chathealth.entity.member.Grade;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

 private final AuthService authService;
 //private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/join")
    public String join(Model model) {
        //model.addAttribute("UserJoinDto", new UserJoinDto());
        return "auth/userjoin";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute UserJoinDto userJoinDto) {
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

        authService.join(insertUserDto);


        return "auth/userjoin";
    }
}
