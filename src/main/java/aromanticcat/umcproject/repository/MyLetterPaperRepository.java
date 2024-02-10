package aromanticcat.umcproject.repository;

import aromanticcat.umcproject.entity.MyLetterPaper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyLetterPaperRepository extends JpaRepository<MyLetterPaper, Long> {
    Page<MyLetterPaper> findByMemberEmail(String email, Pageable pageable);

}
