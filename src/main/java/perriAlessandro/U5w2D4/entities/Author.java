package perriAlessandro.U5w2D4.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "authors")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue
    private UUID id;
    private String nome;
    private String cognome;
    private String mail;
    private LocalDate dataNascita;
    private String imageUrl;
    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<BlogPost> blogPosts;

    public Author(String nome, String cognome, String mail, LocalDate dataNascita, String imageUrl) {
        this.nome = nome;
        this.cognome = cognome;
        this.mail = mail;
        this.dataNascita = dataNascita;
        this.imageUrl = imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        if (nome != null && cognome != null) {
            this.imageUrl = String.format("https://ui-avatars.com/api/?name=%s+%s", nome, cognome);
        } else {
            this.imageUrl = "https://as1.ftcdn.net/v2/jpg/03/46/83/96/1000_F_346839683_6nAPzbhpSkIpb8pmAwufkC7c5eD7wYws.jpg";
        }
    }
}


