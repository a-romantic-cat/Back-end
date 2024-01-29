package aromanticcat.umcproject.repository;

import aromanticcat.umcproject.entity.Letterbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterBoxRepository extends JpaRepository<Letterbox, Long> {
    Letterbox findByMemberId(Long memberId);
}
