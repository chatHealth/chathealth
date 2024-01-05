package chathealth.chathealth.repository;

import chathealth.chathealth.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    public Long countByEmail(String email);

}
