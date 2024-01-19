package chathealth.chathealth.controller;

import chathealth.chathealth.dto.response.ErrorResult;
import chathealth.chathealth.exception.ChatHealthException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> invalidRequestHandler(MethodArgumentNotValidException e) {
        ErrorResult errorResult = ErrorResult.builder()
                .message("잘못된 요청입니다.")
                .code(String.valueOf(BAD_REQUEST.value()))
                .build();
        for (FieldError fieldError : e.getFieldErrors()) {
            errorResult.addValidError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(BAD_REQUEST).body(errorResult);
    }

    @ResponseBody
    @ExceptionHandler(ChatHealthException.class)
    public ResponseEntity<ErrorResult> chatHealthExceptionHandler(ChatHealthException e) {
        int statusCode = e.getStatusCode();

        ErrorResult errorResult = ErrorResult.builder()
                .message(e.getMessage())
                .code(String.valueOf(statusCode))
                .validation(e.getValidation())
                .build();

        return ResponseEntity.status(statusCode).body(errorResult);
    }
}
