package aromanticcat.umcproject.repository;

import aromanticcat.umcproject.entity.NangmanLetter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NangmanLetterRepository extends JpaRepository<NangmanLetter, Long> {

    Page<NangmanLetter> findByHasResponseFalseAndMemberEmailNot(String email, Pageable pageable);

    Page<NangmanLetter> findByMemberEmail(String email, Pageable pageable);

    NangmanLetter findByMemberEmailAndId(String email, Long Id);

    Page<NangmanLetter> findByIsPublicTrueAndHasResponseTrue(Pageable pageable);

    NangmanLetter findByIsPublicTrueAndHasResponseTrueAndId(Long id);
}
