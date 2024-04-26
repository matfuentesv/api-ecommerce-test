package cl.company.ecommerce.exception;

import org.springframework.http.HttpStatus;



public class EcommerceNotFoundException  extends RuntimeException  {

    private HttpStatus status;
    private ErrorResponse errorResponse;

    public EcommerceNotFoundException(ErrorResponse errorResponse, HttpStatus status) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
