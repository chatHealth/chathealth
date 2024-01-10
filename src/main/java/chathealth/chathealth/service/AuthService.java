package chathealth.chathealth.service;


import chathealth.chathealth.constants.Grade;
import chathealth.chathealth.constants.Role;

import chathealth.chathealth.dto.request.EntJoinDto;
import chathealth.chathealth.dto.request.UserJoinDto;
import chathealth.chathealth.dto.response.CustomUserDetails;
import chathealth.chathealth.entity.member.*;
import chathealth.chathealth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${file.path}")
    private String path;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void userJoin(UserJoinDto userJoinDto) { //개인 회원가입
        /*String originalFileName = userJoinDto.getProfile().getOriginalFilename(); //폼에서 들어온 파일네임 받아오기
        UUID uuid = UUID.randomUUID(); //난수 발생
        String fileUrl = path+File.separator+uuid+"_"+originalFileName; //이미지 파일 저장경로 만들기
        String fileName = uuid+"_"+originalFileName; //이미지 파일 이름

        Path imageFilePath = Paths.get(fileUrl); //경로를 Path에 담기
        File saveFile = new File(fileUrl);
        try {
            Files.write(imageFilePath,userJoinDto.getProfile().getBytes()); //이미지 서버에 저장하기
            Thumbnailator.createThumbnail(saveFile,
                    new File(path+File.separator+fileName),150,150); //썸네일 만들기
            saveFile.delete();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        String originalFileName = userJoinDto.getProfile().getOriginalFilename(); //원본 파일 네임
        UUID uuid = UUID.randomUUID(); //난수 발생
        String rename = uuid+"_"+originalFileName; //변경할 이름
        Path filePath = Paths.get(path+rename); //저장할 경로
        try {
            Files.write(filePath,userJoinDto.getProfile().getBytes());
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
                .profile(String.valueOf(filePath))
                .createdDate(userJoinDto.getCreateDate())
                .build();
        memberRepository.save(dbJoinUser);
    }

    @Transactional
    public void entJoin(EntJoinDto entJoinDto) {
        // 사업자 회원가입
        String originalFileName = entJoinDto.getProfile().getOriginalFilename(); //원본 파일 네임
        UUID uuid = UUID.randomUUID(); //난수 발생
        String rename = uuid+"_"+originalFileName; //변경할 이름
        Path filePath = Paths.get(path+rename); //저장할 경로
        try {
            Files.write(filePath,entJoinDto.getProfile().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Member dbJoinEnt = Ent.builder()
                .id(entJoinDto.getId())
                .pw(bCryptPasswordEncoder.encode(entJoinDto.getPw()))
                .birth(entJoinDto.getBirth())
                .role(Role.WAITING_ENT)
                .address(entJoinDto.getAddress())
                .email(entJoinDto.getEmail())
                .company(entJoinDto.getCompany())
                .ceo(entJoinDto.getCeo())
                .entNo(entJoinDto.getEntNo())
                .profile(String.valueOf(filePath))
                .createdDate(entJoinDto.getCreatedDate())
                .build();
        memberRepository.save(dbJoinEnt);
    }
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> loggedMember = memberRepository.findByEmail(email);
        loggedMember.ifPresent(a -> {
            new CustomUserDetails(loggedMember.get());
        });

        throw new UsernameNotFoundException("아이디 혹은 비밀번호를 확인해주세요.");
    }

    public boolean confirmEmail(String email) {
        boolean isExists = memberRepository.existsByEmail(email);
        return isExists;
    }
}