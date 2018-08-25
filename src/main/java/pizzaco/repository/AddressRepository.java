package pizzaco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.Address;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {

    @Query("SELECT a FROM Address a WHERE a.owner.username = :email ORDER BY a.name")
    List<Address> findAllUserAddressesOrderedByName(@Param("email") String email);
}
