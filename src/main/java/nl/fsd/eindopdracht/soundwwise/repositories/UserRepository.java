package nl.fsd.eindopdracht.soundwwise.repositories;

import nl.fsd.eindopdracht.soundwwise.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

    //todo: implement:
//    List<User> findBySomething();
//    Boolean existsByEmailIgnoreCase(String email);
    User findByEmailIgnoreCase(String email);
    Optional<User> findByEmail(String email);
}