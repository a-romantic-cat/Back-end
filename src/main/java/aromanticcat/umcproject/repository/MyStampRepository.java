package aromanticcat.umcproject.repository;

import aromanticcat.umcproject.entity.MyStamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyStampRepository extends JpaRepository<MyStamp, Long> {

    Page<MyStamp> findByMemberEmail(String email, Pageable pageable);

}
