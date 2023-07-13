package my.group.exception;

import my.group.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class BadRequestException  extends RuntimeException{
    private final Logger logger = LoggerFactory.getLogger(BadRequestException.class);

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
