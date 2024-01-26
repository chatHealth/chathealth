package chathealth.chathealth.service;


import chathealth.chathealth.constants.Grade;

import chathealth.chathealth.dto.request.EntJoinDto;
import chathealth.chathealth.dto.request.UserJoinDto;
import chathealth.chathealth.dto.response.CustomUserDetails;
import chathealth.chathealth.entity.member.*;
import chathealth.chathealth.exception.NotPermitted;
import chathealth.chathealth.repository.MemberRepository;
import chathealth.chathealth.util.ImageUpload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import static chathealth.chathealth.constants.Role.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements UserDetailsService {

    private final String domain = "profile";

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ImageUpload imageUpload;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("사용자가 존재하지 않습니다.")
        );
        log.info("member.getRole()====="+member.getRole());
        if(member.getRole().equals(ROLE_WITHDRAW_MEMBER)) {
            log.info("탈퇴한 이메일로 재가입할 수 없습니다.");
            throw new UsernameNotFoundException("탈퇴한 사용자입니다.");
        }
        return new CustomUserDetails(member);
    }


    @Transactional
    public void userJoin(@Valid UserJoinDto userJoinDto) { //개인 회원가입
        String rename = domain+File.separator+imageUpload.uploadImage(userJoinDto.getProfile(),domain);

        Member dbJoinUser = Users.builder()
                .id(userJoinDto.getId())
                .pw(bCryptPasswordEncoder.encode(userJoinDto.getPw()))
                .birth(userJoinDto.getBirth())
                .role(ROLE_USER)
                .address(userJoinDto.getAddress())
                .email(userJoinDto.getEmail())
                .name(userJoinDto.getName())
                .nickname(userJoinDto.getNickname())
                .grade(Grade.BRONZE)
                .profile(rename)
                .createdDate(userJoinDto.getCreateDate())
                .build();
        memberRepository.save(dbJoinUser);
    }

    @Transactional
    public void entJoin(@Valid EntJoinDto entJoinDto) {
        // 사업자 회원가입
        String rename = domain+File.separator+imageUpload.uploadImage(entJoinDto.getProfile(),domain);

        Member dbJoinEnt = Ent.builder()
                .id(entJoinDto.getId())
                .pw(bCryptPasswordEncoder.encode(entJoinDto.getPw()))
                .birth(entJoinDto.getBirth())
                .role(ROLE_WAITING_ENT)
                .address(entJoinDto.getAddress())
                .email(entJoinDto.getEmail())
                .company(entJoinDto.getCompany())
                .ceo(entJoinDto.getCeo())
                .entNo(entJoinDto.getEntNo())
                .profile(rename)
                .createdDate(entJoinDto.getCreatedDate())
                .build();
        memberRepository.save(dbJoinEnt);
    }

    @PreAuthorize("hasAnyRole('ROLE_WAITING_ENT','ROLE_PERMITTED_ENT','ROLE_REJECTED_ENT','ROLE_USER')")
    @Transactional
    public void updatePw(Long id, String pw){
        String newPw = bCryptPasswordEncoder.encode(pw);
        Optional<Member> optionalMember = memberRepository.findById(id);
        if(optionalMember.isPresent()){
            Member findMember = optionalMember.get();
            findMember.updatePw(newPw);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_WAITING_ENT','ROLE_PERMITTED_ENT','ROLE_REJECTED_ENT','ROLE_USER')")
    @Transactional
    public void memberWithdraw(Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent()) {
            Member findMember = optionalMember.get();
            findMember.withdraw(LocalDateTime.now());
            findMember.changeRole(ROLE_WITHDRAW_MEMBER);
        }
    }

    public boolean confirmEmail(String email) {
        boolean isExists = memberRepository.existsByEmail(email);
        return isExists;
    }

}