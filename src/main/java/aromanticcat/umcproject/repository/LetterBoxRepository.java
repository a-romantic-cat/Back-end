package aromanticcat.umcproject.repository;

import aromanticcat.umcproject.entity.Letterbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LetterBoxRepository extends JpaRepository<Letterbox, Long> {
    Letterbox findByMemberId(Long memberId);

    Optional<Letterbox> findById(Long id);
}
