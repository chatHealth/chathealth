package chathealth.chathealth.controller;

import chathealth.chathealth.dto.request.EntJoinDto;
import chathealth.chathealth.dto.request.UserJoinDto;
import chathealth.chathealth.entity.member.Address;
import chathealth.chathealth.entity.member.Grade;
import chathealth.chathealth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
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
        return "auth/user-join";
    }

    @PostMapping("/userjoin") //개인회원가입 처리
    public String userJoin(@ModelAttribute UserJoinDto userJoinDto, MultipartFile profile) {
        //주소 객체화
        Address addressEntity = Address.builder()
                .postcode(userJoinDto.getPostcode())
                .address(userJoinDto.getFrontAddress())
                .addressDetail(userJoinDto.getAddressDetail())
        .build();


        //service에 던질 DTO 빌드
        UserJoinDto insertUserDto = UserJoinDto.builder()
                .email(userJoinDto.getEmail())
                .address(addressEntity)
                .pw(userJoinDto.getPw())
                .name(userJoinDto.getName())
                .nickname(userJoinDto.getNickname())
                .birth(userJoinDto.getBirth())
                .profile(userJoinDto.getProfile())
                .role(userJoinDto.getRole())
                .createDate(LocalDateTime.now())
                .grade(Grade.BRONZE)
                .build();
        authService.userJoin(insertUserDto);
        return "redirect:/auth/user-join";
    }

    @GetMapping("/entjoin") //사업자회원가입창 진입
    public String entJoin(Model model) {
        //model.addAttribute("UserJoinDto", new UserJoinDto());
        return "auth/ent-join";
    }

    @PostMapping("/entjoin") //사업자회원가입 처리
    public String entJoin(@ModelAttribute EntJoinDto entJoinDto, MultipartFile profile) {
        //주소 객체화
        Address addressEntity = Address.builder()
                .postcode(entJoinDto.getPostcode())
                .address(entJoinDto.getFrontAddress())
                .addressDetail(entJoinDto.getAddressDetail())
                .build();
        log.info(String.valueOf(addressEntity));


        //service에 던질 DTO 빌드
        EntJoinDto insertEntDto = EntJoinDto.builder()
                .email(entJoinDto.getEmail())
                .address(addressEntity)
                .pw(entJoinDto.getPw())
                .company(entJoinDto.getCompany())
                .entNo(entJoinDto.getEntNo())
                .ceo(entJoinDto.getCeo())
                .birth(entJoinDto.getBirth())
                .profile(entJoinDto.getProfile())
                .role(entJoinDto.getRole())
                .createdDate(LocalDateTime.now())
                .build();
        log.info("컨트롤러 insertDto = "+String.valueOf(insertEntDto));
        authService.entJoin(insertEntDto);
        return "redirect:/auth/ent-join";
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
