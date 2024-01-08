package chathealth.chathealth.repository;

import chathealth.chathealth.entity.post.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {
}
