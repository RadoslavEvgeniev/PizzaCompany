package pizzaco.repository.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.menu.Dip;

import java.util.Optional;

@Repository
public interface DipRepository extends JpaRepository<Dip, String> {

    Optional<Dip> findByName(String name);
}
