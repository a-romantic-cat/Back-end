package aromanticcat.umcproject.repository;

import aromanticcat.umcproject.entity.NangmanLetter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NangmanLetterRepository extends JpaRepository<NangmanLetter, Long> {

    Page<NangmanLetter> findByHasResponseFalseAndMemberEmailNot(String email, Pageable pageable);

    Page<NangmanLetter> findByMemberEmail(String email, Pageable pageable);

    NangmanLetter findByMemberEmailAndId(String email, Long Id);

    Page<NangmanLetter> findByIsPublicTrueAndHasResponseTrue(Pageable pageable);

    @Query("SELECT n FROM NangmanLetter n " +
            "WHERE n.isPublic = true AND n.hasResponse = true " +
            "ORDER BY n.createdAt DESC")
    Page<NangmanLetter> findLatestLetters(Pageable pageable);

    @Query("SELECT n FROM NangmanLetter n " +
            "WHERE n.isPublic = true AND n.hasResponse = true " +
            "ORDER BY (COALESCE(n.thumbsUpCnt, 0) + COALESCE(n.heartCnt, 0) + COALESCE(n.cryingCnt, 0) + " +
            "COALESCE(n.cloverCnt, 0) + COALESCE(n.clapCnt, 0) + COALESCE(n.starCnt, 0)) DESC")
    Page<NangmanLetter> findPopularLetters(Pageable pageable);

    NangmanLetter findByIsPublicTrueAndHasResponseTrueAndId(Long id);
}
