package aromanticcat.umcproject.repository;

import aromanticcat.umcproject.entity.NangmanLetter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NangmanLetterRepository extends JpaRepository<NangmanLetter, Long> {

    Page<NangmanLetter> findByHasResponseFalse(Pageable pageable);

    Page<NangmanLetter> findByMemberId(Long memberId, Pageable pageable);

    Optional<NangmanLetter> findByMemberIdAndId(Long memberId, Long Id);

    Page<NangmanLetter> findByIsPublicTrueAndHasResponseTrue(Pageable pageable);

    NangmanLetter findByIsPublicTrueAndHasResponseTrueAndId(Long id);
}
