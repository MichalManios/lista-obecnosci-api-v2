package pl.gov.listaobecnosci.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class IncorrectDataException extends RuntimeException {

    public IncorrectDataException(String message) {
        super(message);
    }
}
