package chathealth.chathealth.repository;

import chathealth.chathealth.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


// @repository
public interface PostRepository extends JpaRepository<Post, Long> {

    //@Modifying
    //@Query(value = " ", nativeQuery = true)
    //int a(@Param ("imageId") int imageId, @Param("memberId") int memberId);
}

