package perriAlessandro.U5w2D4.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import perriAlessandro.U5w2D4.payloads.ErrorsResponseDTO;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice // <-- Controller specifico per gestione eccezioni
// Questa classe mi serve per centralizzare la gestione delle eccezioni
public class ExceptionsHandler {

    @ExceptionHandler(BadRequestException.class)
    // Con questa annotazione indico che questo metodo dovrà gestire le eccezioni di tipo BadRequestException
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorsResponseDTO handleBadRequest(BadRequestException ex) {
        if (ex.getErrorsList() != null) {
            // Se c'è la lista degli errori, allora manderemo una risposta contenente la lista degli errori
            String message = ex.getErrorsList().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            return new ErrorsResponseDTO(message, LocalDateTime.now());

        } else {
            // Se non c'è la lista, allora mandiamo un classico payload di errore con un messaggio
            return new ErrorsResponseDTO(ex.getMessage(), LocalDateTime.now());
        }
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    // Con questa annotazione indico che questo metodo dovrà gestire le eccezioni di tipo NotFoundException
    public ErrorsPayload handleNotFound(NotFoundException ex) {
        return new ErrorsPayload(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    public ErrorsPayload handleGenericErrors(Exception ex) {
        ex.printStackTrace(); // Non dimentichiamoci che è ESTREMAMENTE UTILE tenere traccia di chi ha causato l'errore
        return new ErrorsPayload("Problema lato server! Giuro che lo risolveremo presto!", LocalDateTime.now());
    }
}
