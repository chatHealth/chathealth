package chathealth.chathealth.service;

import chathealth.chathealth.dto.request.UserJoinDto;
import chathealth.chathealth.entity.member.Grade;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.entity.member.Role;
import chathealth.chathealth.entity.member.Users;
import chathealth.chathealth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

   @Transactional
    public  Member join(UserJoinDto userJoinDto) {
        Member dbJoinUser = Users.builder()
                .id(userJoinDto.getId())
                .pw(bCryptPasswordEncoder.encode(userJoinDto.getPw()))
                .birth(userJoinDto.getBirth())
                .role(Role.USER)
                .address(userJoinDto.getAddress())
                .email(userJoinDto.getEmail())
                .name(userJoinDto.getName())
                .grade(Grade.BRONZE)
                .report(0)
                .build();
        return memberRepository.save(dbJoinUser);
    }
}
