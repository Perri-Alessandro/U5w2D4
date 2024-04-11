package perriAlessandro.U5w2D4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import perriAlessandro.U5w2D3.entities.Author;

import java.util.Optional;
import java.util.UUID;

public interface AuthorsDAO extends JpaRepository<Author, UUID> {
    boolean existsByMail(String mail);

    Optional<Author> findByMail(String mail);

}
