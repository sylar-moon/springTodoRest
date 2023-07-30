package my.group.exception;

import my.group.model.Response;
import my.group.utility.MyLogger;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {
    private final Logger logger = new MyLogger().getLogger();

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(NoContentException.class)
    public Response handleNoContentException(NoContentException ex) {
        logger.error(ex.getErrorMessage());
        logger.error(ex.getHttpStatus().toString());
        return new Response( ex.getHttpStatus(), ex.getErrorMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public Response handleBadRequestException(BadRequestException ex) {
        return new Response(ex.getObject(), ex.getHttpStatus(), ex.getErrorMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public Response handleNotFoundExceptions(NotFoundException ex) {
        logger.error("not found exception = {}",ex.getHttpStatus());
        return new Response( ex.getHttpStatus(), ex.getErrorMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserConflictException.class)
    public Response handleConflictExceptions(UserConflictException ex) {
        return new Response(ex.getObject(), ex.getHttpStatus(), ex.getErrorMessage());
    }
}