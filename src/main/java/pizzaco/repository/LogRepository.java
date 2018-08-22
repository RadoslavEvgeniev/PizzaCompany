package pizzaco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.Log;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {

    @Query("SELECT l FROM Log l ORDER BY l.dateTime DESC ")
    List<Log> findAllOrderedByDateDesc();
}
