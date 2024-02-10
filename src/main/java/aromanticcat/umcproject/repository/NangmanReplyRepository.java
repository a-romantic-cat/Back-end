package aromanticcat.umcproject.repository;

import aromanticcat.umcproject.entity.NangmanReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface NangmanReplyRepository extends JpaRepository<NangmanReply, Long> {

    boolean existsByMemberEmailAndCreatedAtBetween(String email, LocalDateTime start, LocalDateTime end);

    Page<NangmanReply> findByMemberEmail(String email, Pageable pageable);

    NangmanReply findByNangmanLetter_Id(Long nangmanLetterId);
}
