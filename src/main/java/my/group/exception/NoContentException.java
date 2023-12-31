package my.group.exception;

import org.springframework.http.HttpStatus;

public class NoContentException extends RuntimeException{
    private final String errorMessage;
    private final String url;
    private final HttpStatus httpStatus;
    public NoContentException( String error, String url) {
        super(error);
        this.errorMessage=error;
        this.httpStatus=HttpStatus.NOT_FOUND;
        this.url=url;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getUrl() {
        return url;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
