package chathealth.chathealth.repository.post;

import chathealth.chathealth.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


// @repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    List<Post> findByMemberId(Long id);
    List<Post> findByMaterialListMaterialIdIn(List<Long> materialIds);


    @Query("select p from Post p left join fetch PicturePost pp on p = pp.post where p.id in :list")
    List<Post> findAllByIdIn(List<Long> list);
}

