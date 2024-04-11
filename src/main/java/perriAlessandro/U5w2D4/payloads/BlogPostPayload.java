package perriAlessandro.U5w2D4.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class BlogPostPayload {
    private String titolo;
    private String contenuto;
    private String category;
    private String cover;
    private double minutiLettura;
    private UUID authorId;

}
