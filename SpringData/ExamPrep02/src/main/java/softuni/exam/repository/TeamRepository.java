package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.domain.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team,Integer> {
    Team findByName(String name);
}
