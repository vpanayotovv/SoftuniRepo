package softuni.jsonexercise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.jsonexercise.domain.entities.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {
}
