package git.dimitrikvirik.chessgameapi.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Value("${spring.application.name}")
    private String applicationName;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> ValidExceptionHandler(MethodArgumentNotValidException ex,
                                                        HandlerMethod handlerMethod, HttpServletRequest request){
        String exceptionName = ex.getClass().getName();
        String MethodName = handlerMethod.getMethod().getName();
        return new ResponseEntity<>(ExceptionBody.of(ex, applicationName, MethodName, exceptionName, request.getRequestURI()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> parseExceptionHandler(HttpMessageNotReadableException ex,
                                                        HandlerMethod handlerMethod, HttpServletRequest request){
        String exceptionName = ex.getClass().getName();
        String MethodName = handlerMethod.getMethod().getName();
        return new ResponseEntity<>(ExceptionBody.of(ex, applicationName, MethodName, exceptionName, request.getRequestURI()),
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> otherExceptionHandler(Exception ex,
                                                        HandlerMethod handlerMethod, HttpServletRequest request){
        String exceptionName = ex.getClass().getName();
        String MethodName = handlerMethod.getMethod().getName();
        return new ResponseEntity<>(ExceptionBody.of(ex, applicationName, MethodName, exceptionName, request.getRequestURI()),
                HttpStatus.BAD_REQUEST);
    }

}
