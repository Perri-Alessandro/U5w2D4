package perriAlessandro.U5w2D4.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice // <-- Controller specifico per gestione eccezioni
// Questa classe mi serve per centralizzare la gestione delle eccezioni
public class ExceptionsHandler {

    @ExceptionHandler(BadRequestException.class)
    // Con questa annotazione indico che questo metodo dovrà gestire le eccezioni di tipo BadRequestException
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorsPayload handleBadRequest(BadRequestException ex) {
        return new ErrorsPayload(ex.getMessage(), LocalDateTime.now());
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
