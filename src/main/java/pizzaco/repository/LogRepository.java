package pizzaco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {
}
