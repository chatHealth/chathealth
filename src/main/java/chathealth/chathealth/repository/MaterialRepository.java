package chathealth.chathealth.repository;

import chathealth.chathealth.entity.post.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {

    Material findByMaterialName(String name);
}
