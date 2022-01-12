package git.dimitrikvirik.chessgameapi.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

@Data
public class ExceptionBody {


    private String application_name;
    private String method_name;
    private String exception_name;
    private String status;
    private String message;
    private String timestamp;
    private String path;
    private StackTraceElement[] stackTraceElements;

    public ExceptionBody(String msg, String application_name, String method_name, String exception_name, String path, String status) {
        this.message = msg;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss").withLocale(
                Locale.ENGLISH
        ));
        this.method_name = method_name;
        this.application_name = application_name;
        this.exception_name = exception_name;
        this.path = path;
        this.status = status;
    }


    public static ExceptionBody of(MethodArgumentNotValidException e, String application_name, String method_name, String exception_name, String path) {
        var status = HttpStatus.BAD_REQUEST.name();
        String msg = e.getFieldErrors().stream()
                .map(f -> "Wrong " + f.getField() + ". " + StringUtils.capitalize(f.getDefaultMessage()))
                .collect(Collectors.toList()).toString();

        return new ExceptionBody(msg, application_name, method_name, exception_name, path, status);
    }

    public static ExceptionBody of(HttpMessageNotReadableException e, String application_name, String method_name, String exception_name, String path) {
        var status = HttpStatus.BAD_REQUEST.name();


        InvalidFormatException invalidFormatException = (InvalidFormatException) e.getMostSpecificCause();
        Class<?> targetType = invalidFormatException.getTargetType();

        String simpleName = targetType.getSimpleName();
        String msg = invalidFormatException.getValue() + " can't be parsed to " + simpleName + ".";
        if (targetType.isEnum()) {
            String fields = Arrays.stream(targetType.getFields()).map(Field::getName).collect(Collectors.joining(", "));
            msg += simpleName + " must be one of this: " + fields;
        }

        return new ExceptionBody(msg, application_name, method_name, exception_name, path, status);
    }
//
//    public static ExceptionBody of(Exception e, String application_name, String method_name, String exception_name, String path) {
//        var status = HttpStatus.INTERNAL_SERVER_ERROR.name();
//        String msg = e.getMessage();
//
//        var exceptionBody = new ExceptionBody(msg, application_name, method_name, exception_name, path, status);
//        exceptionBody.setStackTraceElements(e.getStackTrace());
//        return exceptionBody;
//    }

}
