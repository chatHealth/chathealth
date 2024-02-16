package chathealth.chathealth.repository;

import chathealth.chathealth.entity.post.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {


    @Query("select s from Symptom s join fetch s.materialList")
    List<Symptom> findAllFetch();

}
