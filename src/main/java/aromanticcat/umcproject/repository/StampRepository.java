package aromanticcat.umcproject.repository;

import aromanticcat.umcproject.entity.Stamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampRepository extends JpaRepository<Stamp, Long> {

    Page<Stamp> findAll(Pageable pageable);
}
