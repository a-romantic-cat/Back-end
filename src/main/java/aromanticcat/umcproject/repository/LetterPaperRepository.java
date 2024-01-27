package aromanticcat.umcproject.repository;

import aromanticcat.umcproject.entity.LetterPaper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterPaperRepository extends JpaRepository<LetterPaper, Long> {

    Page<LetterPaper> findAll(Pageable pageable);
}
