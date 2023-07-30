package my.group.exception;

import org.springframework.http.HttpStatus;

public class UserConflictException extends RuntimeException{
    private final String errorMessage;
    private final String url;
    private final HttpStatus httpStatus;
    private final Object object;

    public UserConflictException( String error, String url, Object object) {
        super(error);
        this.errorMessage=error;
        this.httpStatus=HttpStatus.CONFLICT;
        this.url=url;
        this.object=object;
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

    public Object getObject() {
        return object;
    }
}
