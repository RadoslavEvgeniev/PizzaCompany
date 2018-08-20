package pizzaco.repository.ingredients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.pizza.Size;

import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, String> {

    Optional<Size> findBySize(String size);
}
