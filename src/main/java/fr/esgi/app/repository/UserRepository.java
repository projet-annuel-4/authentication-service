package fr.esgi.app.repository;

import fr.esgi.app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByOrderByIdAsc();

    User findByActivationCode(String code);

    User findByEmail(String email);

    User findByPasswordResetCode(String code);

}
