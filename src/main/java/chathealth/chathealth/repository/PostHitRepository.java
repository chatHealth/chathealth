package chathealth.chathealth.repository;

import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.entity.post.Post;
import chathealth.chathealth.entity.post.PostHit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostHitRepository extends JpaRepository<PostHit, Long> {
    List<PostHit> findByMemberAndPost(Member member, Post post);
    LocalDateTime findCreatedDateByMember(Member member);

    @Query("SELECT ph FROM PostHit ph WHERE ph.member = :member and ph.post=:post ORDER BY ph.createdDate DESC")
    List<Optional<PostHit>> findTopByMemberAndPostOrderByCreateDateDesc(Member member,Post post);


}
