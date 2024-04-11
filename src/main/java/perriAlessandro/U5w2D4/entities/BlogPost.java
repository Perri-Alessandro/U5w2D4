package perriAlessandro.U5w2D4.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "blogPost")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BlogPost {
    @Id
    @GeneratedValue
    private UUID id;
    private String category;
    private String titolo;
    private String cover;
    private String contenuto;
    private double minutiLettura;
    @ManyToOne
    @JoinColumn(name = "authorId")
    private Author author;

}
