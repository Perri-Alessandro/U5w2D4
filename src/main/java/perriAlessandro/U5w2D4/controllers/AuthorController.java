package perriAlessandro.U5w2D4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import perriAlessandro.U5w2D3.entities.Author;
import perriAlessandro.U5w2D3.services.AuthorService;

import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;


    // GET .../authors
    @GetMapping
    public Page<Author> getAllBlogPost(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String sortBy) {
        return this.authorService.getAuthorList(page, size, sortBy);
    }

    // POST .../authors (+ body)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Status Code 201
    public Author saveAuthor(@RequestBody Author body) {
        return this.authorService.saveAuthor(body);
    }

    // GET .../authors/{authId}
    @GetMapping("/{authId}")
    private Author findBlogById(@PathVariable UUID authId) {
        return this.authorService.findById(authId);
    }

    // PUT .../authors/{authId} (+ body)
    @PutMapping("/{authId}")
    private Author findBlogByIdAndUpdate(@PathVariable UUID authId, @RequestBody Author body) {
        return this.authorService.findByIdAndUpdate(authId, body);
    }

    // DELETE .../authors/{authId}
    @DeleteMapping("/{authId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Status Code 204
    private void findByBlogIdAndDelete(@PathVariable UUID authId) {
        this.authorService.findByIdAndDelete(authId);
    }
}
