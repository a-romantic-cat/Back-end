package aromanticcat.umcproject.repository;

import aromanticcat.umcproject.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
}
