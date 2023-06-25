package my.group.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException  extends RuntimeException{
    private final String errorMessage;
    private final String url;
    private final HttpStatus httpStatus;
    private final Object object;

    public BadRequestException(String errorMessage, String url, Object object) {
        this.errorMessage = errorMessage;
        this.url = url;
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.object = object;
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
