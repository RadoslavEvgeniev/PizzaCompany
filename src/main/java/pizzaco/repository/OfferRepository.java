package pizzaco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {
}
