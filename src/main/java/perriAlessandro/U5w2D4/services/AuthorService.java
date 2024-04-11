package perriAlessandro.U5w2D4.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import perriAlessandro.U5w2D4.entities.Author;
import perriAlessandro.U5w2D4.exceptions.BadRequestException;
import perriAlessandro.U5w2D4.exceptions.NotFoundException;
import perriAlessandro.U5w2D4.payloads.NewAuthorDTO;
import perriAlessandro.U5w2D4.repositories.AuthorsDAO;
import perriAlessandro.U5w2D4.repositories.BlogPostDAO;

import java.io.IOException;
import java.util.UUID;

@Service
public class AuthorService {
    @Autowired
    private AuthorsDAO authDAO;

    @Autowired
    private BlogPostDAO blogPostDAO;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Page<Author> getAuthorList(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.authDAO.findAll(pageable);
    }

    public Author saveAuthor(NewAuthorDTO body) {
        if (authDAO.findByMail(body.mail()).isPresent()) {
            throw new BadRequestException("L'email " + body.mail() + " è già in uso!");
        }
        Author newUser = new Author(body.nome(), body.cognome(), body.mail(), body.dataNascita(),
                "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());

        // 4. Salvo lo user
        return authDAO.save(newUser);
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
        Author found = this.findById(id);
        authDAO.delete(found);
    }

    public String uploadImage(MultipartFile image) throws IOException {
        String url = (String) cloudinaryUploader.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");
        return url;
    }

}
