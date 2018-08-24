package pizzaco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.Address;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {

    @Query("SELECT a FROM Address a WHERE a.owner.username = :email")
    List<Address> findAllUserAdressesOrderedByName(@Param("email") String email);
}
