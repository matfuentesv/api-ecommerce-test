package cl.company.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;


public class EcommerceNotFoundException  extends ResponseStatusException {

    private ErrorResponse errorResponse;

    public EcommerceNotFoundException( HttpStatus status, String message) {
        super(status, message);
    }
}
