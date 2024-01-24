package chathealth.chathealth.service;

import chathealth.chathealth.dto.request.EntEditDto;
import chathealth.chathealth.dto.request.UserEditDto;
import chathealth.chathealth.dto.response.EntInfoDto;
import chathealth.chathealth.dto.response.PostLikeDto;
import chathealth.chathealth.dto.response.UserInfoDto;
import chathealth.chathealth.entity.member.Ent;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.constants.Role;
import chathealth.chathealth.entity.member.Users;
import chathealth.chathealth.entity.post.PostLike;
import chathealth.chathealth.exception.NotPermitted;
import chathealth.chathealth.exception.UserNotFound;
import chathealth.chathealth.repository.MemberRepository;
import chathealth.chathealth.repository.PostLikeRepository;
import chathealth.chathealth.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static chathealth.chathealth.constants.Role.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;


    @Value("${file.path}")
    private String path;
    private String domain = "profile"+File.separator;


    public UserInfoDto getUserInfo(Long id) {

        Member member = memberRepository.findById(id).orElseThrow(
                UserNotFound::new
        );

        Users findUser = toUser(member);
        return UserInfoDto.builder()
                .id(findUser.getId())
                .name(findUser.getName())
                .nickname(findUser.getNickname())
                .email(findUser.getEmail())
                .profile(findUser.getProfile())
                .birth(findUser.getBirth())
                .deletedDate(findUser.getDeletedDate())
                .grade(findUser.getGrade())
                .role(findUser.getRole())
                .address(findUser.getAddress())
                .build();
    }
    public EntInfoDto getEntInfo(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                UserNotFound::new
        );

        Ent findEnt = toEnt(member);

        return EntInfoDto.builder()
                .id(findEnt.getId())
                .email(findEnt.getEmail())
                .address(findEnt.getAddress())
                .birth(findEnt.getBirth())
                .profile(findEnt.getProfile())
                .deletedDate(findEnt.getDeletedDate())
                .role(findEnt.getRole())
                .ceo(findEnt.getCeo())
                .company(findEnt.getCompany())
                .entNo(findEnt.getEntNo())
                .build();
    }

    public void updateUserInfo(Long id, UserEditDto userEditDto) {
        Member member =  memberRepository.findById(id).orElseThrow(
                UserNotFound::new
        );
        Users findUser = toUser(member);

        findUser.update(userEditDto);

    }

    public void updateEntInfo(Long id, EntEditDto entEditDto) {
        Member member = memberRepository.findById(id).orElseThrow(
                UserNotFound::new
        );
        Ent findEnt = toEnt(member);

        findEnt.update(entEditDto);
    }

    public void updateProfile(Long id, MultipartFile changeProfile) {
        String originalFileName = changeProfile.getOriginalFilename(); //원본 파일 네임
        UUID uuid = UUID.randomUUID(); //난수 발생
        String uploadMon = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")) + File.separator;
        String rename = domain+uploadMon+uuid+"_"+originalFileName; //변경할 이름
        Path filePath = Paths.get(path+rename); //저장할 실제경로
        File checkPath = new File(path+domain+uploadMon); //월별 파일 있는지 체크용도

        if (!checkPath.exists()) {
            boolean created = checkPath.mkdirs();
            if (!created) {
                throw new NotPermitted("디렉터리 생성에 실패했습니다.");
            }
        }

        try {
            Files.write(filePath,changeProfile.getBytes());
            Optional<Member> optionalMember = memberRepository.findById(id);
            if(optionalMember.isPresent()){
                Member findMember = optionalMember.get();
                findMember.updateProfile(rename);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PostLikeDto> getPostLike(Long id){
        List<PostLikeDto> dto = postLikeRepository.findByMemberId(id).stream()
                .map(postLike -> PostLikeDto.builder()
                        .memberId(postLike.getMember().getId())
                        .postId(postLike.getPost().getId())
                        .title(postLike.getPost().getTitle())
                        .company(postLike.getPost().getMember().getCompany())
                        .build())
                .toList();
        return dto;
    }

    public List<EntInfoDto> getEntInfoList(Long id) {
        Ent findEnt = (Ent) memberRepository.findById(id).orElseThrow(
                UserNotFound::new
        );
        return memberRepository.findByEmail(findEnt.getEmail()).stream()
                .map(u->EntInfoDto.builder()
                        .id(findEnt.getId())
                        .email(findEnt.getEmail())
                        .address(findEnt.getAddress())
                        .birth(findEnt.getBirth())
                        .profile(findEnt.getProfile())
                        .deletedDate(findEnt.getDeletedDate())
                        .role(findEnt.getRole())
                        .ceo(findEnt.getCeo())
                        .company(findEnt.getCompany())
                        .entNo(findEnt.getEntNo())
                        .build()
                )
                .toList();

    }

    private static Users toUser(Member member) {
        if (!(member instanceof Users)) throw new UserNotFound();
        return (Users) member;
    }

    private static Ent toEnt(Member member) {
        List<Role> entRole = List.of(ROLE_PERMITTED_ENT, ROLE_REJECTED_ENT, ROLE_WAITING_ENT);

        if (!entRole.contains(member.getRole())) {
            throw new UserNotFound();
        }
        return (Ent) member;
    }
}