package espol.edu.ec.bikeusuario.utils;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.exception.DataException;
import org.hibernate.id.IdentifierGenerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class ManejadorExcepciones {
	
	private static Logger logger = LoggerFactory.getLogger(ManejadorExcepciones.class);
	
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorDetalles> handleRunTimeException(RuntimeException e) {
    	
    	if (e.getCause().getCause() instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e.getCause().getCause()).getConstraintViolations();
            Set<String> messages = new HashSet<>(constraintViolations.size());
        	messages.addAll(constraintViolations.stream()
        	        .map(constraintViolation -> String.format("%s", constraintViolation.getMessage()))
        	        .collect(Collectors.toList()));

        	return error(BAD_REQUEST, e, messages.toString());
       }
    	
        return error(INTERNAL_SERVER_ERROR, e, e.getMessage());
    }
    
    @ExceptionHandler({JpaSystemException.class})
    public ResponseEntity<ErrorDetalles> handleJpaSystemException(JpaSystemException e) {
    	if(e.getCause() instanceof IdentifierGenerationException) {
    		return error(NOT_FOUND, null, "Error al obtener la secuencia");
    	}
        return error(NOT_FOUND, e, e.getMessage());
    }
    
    @ExceptionHandler({DataException.class})
    public ResponseEntity<ErrorDetalles> handleDataException(DataException e) {
        return error(BAD_REQUEST, e, e.getMessage());
    }
    
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ErrorDetalles> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return error(BAD_REQUEST, e, "Error, problemas de integridad de datos. " + e.getMessage());
    }
    
    @ExceptionHandler({NoResultException.class})
    public ResponseEntity<ErrorDetalles> handleNoResultException(NoResultException e) {
        return error(BAD_REQUEST, e, e.getMessage());
    }
    
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorDetalles> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    	List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        return error(BAD_REQUEST, e, errors.toString());
    }
    
    //
    public static ResponseEntity<ErrorDetalles> error(HttpStatus status, Exception e, String mensaje) {
    	logger.error("Exception : ", e != null ? e.getMessage() : "");
    	logger.error("Exception completa", e);
    	
    	ErrorDetalles errorDetails = new ErrorDetalles(new Date(), mensaje,
    			e != null ? e.getMessage() : "");
    	
        return new ResponseEntity<ErrorDetalles>(errorDetails, status);
    }
}