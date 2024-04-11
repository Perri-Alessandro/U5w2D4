package perriAlessandro.U5w2D4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import perriAlessandro.U5w2D4.entities.Author;
import perriAlessandro.U5w2D4.entities.BlogPost;
import perriAlessandro.U5w2D4.exceptions.NotFoundException;
import perriAlessandro.U5w2D4.payloads.BlogPostPayload;
import perriAlessandro.U5w2D4.repositories.BlogPostDAO;

import java.util.UUID;

@Service
public class BlogPostService {
    @Autowired
    private BlogPostDAO blogDAO;

    @Autowired
    private AuthorService authorService;

    public Page<BlogPost> getBlogPostList(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.blogDAO.findAll(pageable);
    }

    public BlogPost saveBlogPost(BlogPostPayload body) {
        Author author = authorService.findById(body.getAuthorId());
        BlogPost newBlogPost = new BlogPost();
        newBlogPost.setMinutiLettura(body.getMinutiLettura());
        newBlogPost.setContenuto(body.getContenuto());
        newBlogPost.setTitolo(body.getTitolo());
        newBlogPost.setAuthor(author);
        newBlogPost.setCategory(body.getCategory());
        newBlogPost.setCover("http://picsum.photos/200/300");
        return blogDAO.save(newBlogPost);
    }

    public BlogPost findById(UUID id) {
        return this.blogDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public BlogPost findByIdAndUpdate(UUID id, BlogPostPayload updatedBlog) {
        BlogPost found = this.findById(id);
        found.setContenuto(updatedBlog.getContenuto());
        found.setCategory(updatedBlog.getCategory());
        found.setTitolo(updatedBlog.getTitolo());
        found.setCover(updatedBlog.getCover());
        found.setMinutiLettura(updatedBlog.getMinutiLettura());
        return blogDAO.save(found);
    }

    public void findByIdAndDelete(UUID id) {
        BlogPost found = this.findById(id);
        found.setAuthor(null);
        this.blogDAO.save(found); // Salva le modifiche al post senza l'autore
        // Ora puoi eliminare il post
        this.blogDAO.delete(found);
    }
}
