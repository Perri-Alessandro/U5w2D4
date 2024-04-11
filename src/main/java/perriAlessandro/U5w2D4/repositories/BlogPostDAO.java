package perriAlessandro.U5w2D4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import perriAlessandro.U5w2D3.entities.Author;
import perriAlessandro.U5w2D3.entities.BlogPost;

import java.util.List;
import java.util.UUID;

public interface BlogPostDAO extends JpaRepository<BlogPost, UUID> {
    boolean existsByTitolo(String titolo);

    List<BlogPost> findByAuthor(Author author);
}
