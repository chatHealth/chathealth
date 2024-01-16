package chathealth.chathealth.repository.post;

import chathealth.chathealth.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;


// @repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    //@Modifying
    //@Query(value = " ", nativeQuery = true)
    //int a(@Param ("imageId") int imageId, @Param("memberId") int memberId);
}

