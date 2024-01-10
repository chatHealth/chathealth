package chathealth.chathealth.service;

import chathealth.chathealth.dto.request.EntEditDto;
import chathealth.chathealth.dto.request.UserEditDto;
import chathealth.chathealth.dto.response.EntInfoDto;
import chathealth.chathealth.dto.response.UserInfoDto;
import chathealth.chathealth.entity.member.Ent;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.constants.Role;
import chathealth.chathealth.entity.member.Users;
import chathealth.chathealth.exception.UserNotFound;
import chathealth.chathealth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static chathealth.chathealth.constants.Role.*;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


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
                .address(findUser.getAddress())
                .build();
    }
    public UserInfoDto getUserInfo(String email) {

        Member member = memberRepository.findByEmail(email).orElseThrow(
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
                .address(findUser.getAddress())
                .role(findUser.getRole())
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
    public EntInfoDto getEntInfo(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
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
        Member member = memberRepository.findById(id).orElseThrow(
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

    private static Users toUser(Member member) {
        if (member.getRole() != USER) {
            throw new UserNotFound();
        }
        return (Users) member;
    }

    private static Ent toEnt(Member member) {
        List<Role> entRole = List.of(PERMITTED_ENT, REJECTED_ENT, WAITING_ENT);

        if (!entRole.contains(member.getRole())) {
            throw new UserNotFound();
        }
        return (Ent) member;
    }
}