package chathealth.chathealth.service;

import chathealth.chathealth.constants.Role;
import chathealth.chathealth.dto.request.member.EntEditDto;
import chathealth.chathealth.dto.request.member.UserEditDto;
import chathealth.chathealth.dto.response.member.*;
import chathealth.chathealth.entity.member.Ent;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.entity.member.Users;
import chathealth.chathealth.exception.UserNotFound;
import chathealth.chathealth.repository.MemberRepository;
import chathealth.chathealth.repository.PostLikeRepository;
import chathealth.chathealth.repository.ReViewRepository;
import chathealth.chathealth.repository.post.PostRepository;
import chathealth.chathealth.util.ImageUpload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static chathealth.chathealth.constants.Role.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final ReViewRepository reviewRepository;
    private final ImageUpload imageUpload;


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
        String domain = "profile";
        String newProfile = domain +File.separator+imageUpload.uploadImage(changeProfile, domain);
        Optional<Member> optionalMember = memberRepository.findById(id);
        if(optionalMember.isPresent()){
            Member findMember = optionalMember.get();
            findMember.updateProfile(newProfile);
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

    public List<MyReviewDto> getMyReview(Long id){
        return reviewRepository.findByMemberId(id).stream()
                .map(myReview -> MyReviewDto.builder()
                        .memberId(myReview.getMember().getId())
                        .postId(myReview.getPost().getId())
                        .title(myReview.getPost().getTitle())
                        .createdDate(myReview.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .build())
                .toList();
    }

    public List<MyPostDto> getMyPost(Long id) {
        return postRepository.findByMemberId(id).stream()
                .map(myPost-> MyPostDto.builder()
                        .memberId(myPost.getMember().getId())
                        .postId(myPost.getId())
                        .title(myPost.getTitle())
                        .createdDate(myPost.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .build())
                .toList();
    }
    public List<UserInfoDto> getUserList() {

        return memberRepository.findByDeletedDateIsNullAndRoleInOrderByRole(getUserRoles()).stream().filter(test -> test instanceof Users).map(
                test -> {
                    Users users = (Users) test;
                    return UserInfoDto.builder()
                            .nickname(users.getNickname())
                            .name(users.getName())
                            .email(users.getEmail())
                            .grade(users.getGrade())
                            .address(users.getAddress())
                            .id(users.getId())
                            .role(users.getRole())
                            .build();
                }
        ).toList();
    }

    public List<EntInfoDto> getEntList() {

        return memberRepository.findByDeletedDateIsNullAndRoleInOrderByRole(getEntRoles()).stream().filter(test -> test instanceof Ent).map(
                test -> {
                    Ent ent = (Ent) test;
                    return EntInfoDto.builder()
                            .company(ent.getCompany())
                            .ceo(ent.getCeo())
                            .email(ent.getEmail())
                            .entNo(ent.getEntNo())
                            .id(ent.getId())
                            .role(ent.getRole())
                            .build();
                }
        ).toList();
    }

    @Transactional
    public void changeEntRoles(Long id, Role role){
        Member member = memberRepository.findById(id).orElseThrow(
                UserNotFound::new
        );
        Ent findMember = toEnt(member);
        findMember.changeRole(role);
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