package aromanticcat.umcproject.repository;

import aromanticcat.umcproject.entity.AcquiredItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcquiredItemRepository extends JpaRepository<AcquiredItem, Long>{

    List<AcquiredItem> findByMemberEmail(String email);
    Page<AcquiredItem> findByMemberEmailAndLetterPaperIdIsNotNull(String email, Pageable pageable);
    Page<AcquiredItem> findByMemberEmailAndStampIdIsNotNull(String email, Pageable pageable);


    boolean existsByMemberEmailAndLetterPaperId(String email, Long letterPaperId);
    boolean existsByMemberEmailAndStampId(String email, Long stampId);



}
