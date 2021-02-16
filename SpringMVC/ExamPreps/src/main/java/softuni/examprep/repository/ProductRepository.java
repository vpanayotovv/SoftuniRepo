package softuni.examprep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.examprep.model.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {
}
