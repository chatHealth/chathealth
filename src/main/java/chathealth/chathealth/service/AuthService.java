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
import net.coobird.thumbnailator.Thumbnailator;
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
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void join(UserJoinDto userJoinDto) {
        //이미지
        String uploadDir = System.getenv("upload"); //환경변수로 저장할 폴더 가져오기
        String originalFileName = userJoinDto.getProfile().getOriginalFilename(); //폼에서 들어온 파일네임 받아오기
        UUID uuid = UUID.randomUUID(); //난수 발생
        String imageFileName = uuid+"_"+originalFileName; //저장할 이미지파일 이름 만들어주기
        String thumbnailFileName = "thumb_"+imageFileName; //미리보기파일 이름 정해주기

        Path imageFilePath = Paths.get(uploadDir+File.separator+imageFileName); //이미지 저장경로 만들기
        File originalFile = new File(uploadDir+File.separator+imageFileName); //이미지 서버에 저장하기
        try {
            Files.write(imageFilePath,userJoinDto.getProfile().getBytes());
            Thumbnailator.createThumbnail(originalFile,
                    new File(uploadDir+File.separator+thumbnailFileName),150,150); //썸네일 만들기
            originalFile.delete();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Member dbJoinUser = Users.builder()
                .id(userJoinDto.getId())
                .pw(bCryptPasswordEncoder.encode(userJoinDto.getPw()))
                .birth(userJoinDto.getBirth())
                .role(Role.USER)
                .address(userJoinDto.getAddress())
                .email(userJoinDto.getEmail())
                .name(userJoinDto.getName())
                .grade(Grade.BRONZE)
                .profile(String.valueOf(imageFilePath))
                .createdDate(userJoinDto.getCreateDate())
                .build();
        log.info(String.valueOf(dbJoinUser));
        memberRepository.save(dbJoinUser);
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Optional<Member> loggedMember = memberRepository.findByEmail(email);
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