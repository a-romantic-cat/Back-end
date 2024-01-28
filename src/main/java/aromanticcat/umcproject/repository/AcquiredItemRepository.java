package aromanticcat.umcproject.repository;

import aromanticcat.umcproject.entity.AcquiredItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcquiredItemRepository extends JpaRepository<AcquiredItem, Long>{

    List<AcquiredItem> findByMemberId(Long memberId);
    Page<AcquiredItem> findByMemberIdAndLetterPaperIdIsNotNull(Long memberId, Pageable pageable);

}
