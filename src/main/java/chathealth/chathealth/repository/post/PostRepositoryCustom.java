package chathealth.chathealth.repository.post;

import chathealth.chathealth.dto.request.PostSearch;
import chathealth.chathealth.entity.post.Post;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getPosts(PostSearch postSearch);

    Long getPostsCount(PostSearch postSearch);

    List<Post> getBestPostsPerDay();

    List<Post> getBestPostsPerWeek();

}
