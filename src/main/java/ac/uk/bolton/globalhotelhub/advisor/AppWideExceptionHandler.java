package ac.uk.bolton.globalhotelhub.advisor;

import ac.uk.bolton.globalhotelhub.exception.InternalServerErrorException;
import ac.uk.bolton.globalhotelhub.util.AjaxResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
@RestControllerAdvice
public class AppWideExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public AjaxResponse<Object> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return AjaxResponse.error(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public AjaxResponse<Object> handleInternalServerErrorException(InternalServerErrorException ex) {
        return AjaxResponse.error(ex.getMessage());
    }
}
