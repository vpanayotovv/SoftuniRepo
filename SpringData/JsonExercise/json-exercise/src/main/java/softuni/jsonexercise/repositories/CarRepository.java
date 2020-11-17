package softuni.jsonexercise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.jsonexercise.domain.entities.Car;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
}
