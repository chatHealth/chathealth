package chathealth.chathealth.service;

import chathealth.chathealth.dto.request.UserJoinDto;
import chathealth.chathealth.dto.response.CustomUserDetails;
import chathealth.chathealth.entity.member.Grade;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.entity.member.Role;
import chathealth.chathealth.entity.member.Users;
import chathealth.chathealth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void join(UserJoinDto userJoinDto) {
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
        memberRepository.save(dbJoinUser);
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Optional<Member> loggedMember = memberRepository.findByEmail(email);
        log.info("UserDetails  44");
        if(loggedMember.isPresent()) {
            return new CustomUserDetails(loggedMember.get());
        }
        throw new UsernameNotFoundException("아이디 혹은 비밀번호를 확인해주세요.");
    }

    public int confirmEmail(String email) {
        int count = memberRepository.countByEmail(email).intValue();
        System.out.println(count);
        return count;
    }
}