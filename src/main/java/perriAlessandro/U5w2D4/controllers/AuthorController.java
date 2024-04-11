package perriAlessandro.U5w2D4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import perriAlessandro.U5w2D4.entities.Author;
import perriAlessandro.U5w2D4.exceptions.BadRequestException;
import perriAlessandro.U5w2D4.payloads.NewAuthorDTO;
import perriAlessandro.U5w2D4.payloads.NewAuthorRespDTO;
import perriAlessandro.U5w2D4.services.AuthorService;

import java.io.IOException;
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
    public NewAuthorRespDTO saveAuthor(@RequestBody @Validated NewAuthorDTO body, BindingResult validation) {
        // BindingResult validation ci serve per valutare il risultato di questa validazione
        if (validation.hasErrors()) { // Se ci sono stati errori di validazione dovrei triggerare un 400 Bad Request
            throw new BadRequestException(validation.getAllErrors()); // Inviamo la lista degli errori all'Error Handler opportuno
        }
        // Altrimenti se non ci sono stati errori posso salvare tranquillamente lo user
        return new NewAuthorRespDTO(this.authorService.saveAuthor(body).getId());
    }

    // GET .../authors/{authId}
    @GetMapping("/{authId}")
    private Author findBlogById(@PathVariable UUID authId) {
        return authorService.findById(authId);
    }

    // PUT .../authors/{authId} (+ body)
    @PutMapping("/{authId}")
    private Author findBlogByIdAndUpdate(@PathVariable UUID authId, @RequestBody Author body) {
        return authorService.findByIdAndUpdate(authId, body);
    }

    // DELETE .../authors/{authId}
    @DeleteMapping("/{authId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Status Code 204
    private void findByBlogIdAndDelete(@PathVariable UUID authId) {
        authorService.findByIdAndDelete(authId);
    }

    @PostMapping("/upload")
    public String uploadAvatar(@RequestParam("imageUrl") MultipartFile image) throws IOException {
        // "avatar" deve corrispondere ESATTAMENTE alla chiave del Multipart dove sarà contenuto il file
        // altrimenti il file non verrà trovato
        return this.authorService.uploadImage(image);

    }
}
