package pizzaco.repository.ingredients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.ingredients.Size;

import java.util.List;
import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, String> {

    Optional<Size> findBySize(String size);

    @Query("SELECT s FROM Size  s ORDER BY s.numberOfSlices")
    List<Size> findAllOrderByNumberOfSlices();
}
