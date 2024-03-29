package aromanticcat.umcproject.repository;

import aromanticcat.umcproject.entity.LetterPaper;
import aromanticcat.umcproject.entity.Stamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LetterPaperRepository extends JpaRepository<LetterPaper, Long> {
    Optional<LetterPaper> findById(Long id);
    Page<LetterPaper> findAll(Pageable pageable);
}
