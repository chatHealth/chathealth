package chathealth.chathealth.repository;

import chathealth.chathealth.constants.Role;
import chathealth.chathealth.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MemberRepository extends JpaRepository<Member, Long>{
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Member> findByDeletedDateIsNullAndRoleInOrderByRole(Set<Role> set);
}
