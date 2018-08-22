package pizzaco.repository.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.menu.Pasta;

import java.util.List;
import java.util.Optional;

@Repository
public interface PastaRepository extends JpaRepository<Pasta, String> {

    Optional<Pasta> findByName(String name);

    @Query("SELECT p FROM Pasta p ORDER BY p.name")
    List<Pasta> findAllOrderedAlphabetically();
}
