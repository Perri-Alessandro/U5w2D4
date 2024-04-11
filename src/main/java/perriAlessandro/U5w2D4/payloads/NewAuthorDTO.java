package perriAlessandro.U5w2D4.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewAuthorDTO(@NotEmpty(message = "Il nome proprio è obbligatorio")
                           @Size(min = 3, max = 30, message = "Il nome proprio deve essere compreso tra i 3 e i 30 caratteri")
                           String nome,
                           @NotEmpty(message = "Il cognome è obbligatorio")
                           @Size(min = 3, max = 30, message = "Il cognome deve essere compreso tra i 3 e i 30 caratteri")
                           String cognome,
                           @NotEmpty(message = "L'email è obbligatoria")
                           @Email(message = "L'email inserita non è valida")
                           String mail,
                           @NotEmpty(message = "La data di nascita è obbligatoria")
                           @Size(min = 4, message = "La data deve avere come minimo 4 caratteri")
                           LocalDate dataNascita) {
}
