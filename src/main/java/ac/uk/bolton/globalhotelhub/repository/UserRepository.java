package ac.uk.bolton.globalhotelhub.repository;

import ac.uk.bolton.globalhotelhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
