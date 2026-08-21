package br.com.atenda360.shared;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.*;
@RestControllerAdvice public class ApiExceptionHandler{
 @ExceptionHandler(NoSuchElementException.class) ResponseEntity<?> notFound(NoSuchElementException e){return ResponseEntity.status(404).body(Map.of("timestamp",Instant.now(),"mensagem",e.getMessage()));}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<?> validation(MethodArgumentNotValidException e){var erros=e.getFieldErrors().stream().map(f->Map.of("campo",f.getField(),"mensagem",Objects.requireNonNullElse(f.getDefaultMessage(),"inválido"))).toList();return ResponseEntity.badRequest().body(Map.of("timestamp",Instant.now(),"erros",erros));}
 @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<?> badRequest(IllegalArgumentException e){return ResponseEntity.badRequest().body(Map.of("timestamp",Instant.now(),"mensagem",e.getMessage()));}
}
