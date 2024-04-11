package perriAlessandro.U5w2D4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import perriAlessandro.U5w2D4.entities.Author;
import perriAlessandro.U5w2D4.entities.BlogPost;
import perriAlessandro.U5w2D4.exceptions.BadRequestException;
import perriAlessandro.U5w2D4.exceptions.NotFoundException;
import perriAlessandro.U5w2D4.repositories.AuthorsDAO;
import perriAlessandro.U5w2D4.repositories.BlogPostDAO;

import java.util.List;
import java.util.UUID;

@Service
public class AuthorService {
    @Autowired
    private AuthorsDAO authDAO;

    @Autowired
    private BlogPostDAO blogPostDAO;

    public Page<Author> getAuthorList(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.authDAO.findAll(pageable);
    }

    public Author saveAuthor(Author body) {
        this.authDAO.findByMail(body.getMail()).ifPresent(
                // 2. Se lo è triggero un errore
                user -> {
                    throw new BadRequestException("L'email " + user.getMail() + " è già in uso!");
                }
        );
        return authDAO.save(body);
    }

    public Author findById(UUID id) {
        return this.authDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Author findByIdAndUpdate(UUID id, Author updatedAut) {
        Author found = this.findById(id);
        found.setNome(updatedAut.getNome());
        found.setCognome(updatedAut.getCognome());
        found.setMail(updatedAut.getMail());
        found.setDataNascita(updatedAut.getDataNascita());
        found.setImageUrl(updatedAut.getImageUrl());
        found.setBlogPosts(updatedAut.getBlogPosts());
        return authDAO.save(found);
    }

    public void findByIdAndDelete(UUID id) {
        Author author = authDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found with id: " + id));

        List<BlogPost> blogPosts = author.getBlogPosts();

        if (!blogPosts.isEmpty()) {
            // Elimina tutti i post del blog associati all'autore
            blogPosts.forEach(blogPost -> {
                blogPost.setAuthor(null); // Rimuovi l'autore dal post del blog
                blogPostDAO.save(blogPost); // Salva il post del blog aggiornato
            });
        }

        // Una volta eliminati tutti i post del blog associati, elimina l'autore
        authDAO.delete(author);
    }

}
