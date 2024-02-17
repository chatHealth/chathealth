package chathealth.chathealth.repository;

import chathealth.chathealth.entity.post.Post;
import chathealth.chathealth.entity.post.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
    List<PostLike> findByMemberId(Long memberId);
    Long countByPostId(Long postId);
    PostLike findByMemberIdAndPostId(Long memberId,Long postId);
    void deleteByMemberIdAndPostId(Long memberId, Long postId);

}
