package chathealth.chathealth.repository;

import chathealth.chathealth.entity.post.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
    List<PostLike> findByMemberId(Long memberId);


}
