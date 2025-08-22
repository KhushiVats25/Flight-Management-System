package system.flight.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import system.flight.dto.ErrorResponseDTO;

import java.time.LocalDateTime;

public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(Exception ex, HttpStatus status, String error){
        ErrorResponseDTO responseDTO=new ErrorResponseDTO(LocalDateTime.now(),
                status.value(),
                error,
                ex.getMessage()
        );

        return new ResponseEntity<>(responseDTO, status);
    }

    private ResponseEntity<ErrorResponseDTO>  resourceAlreadyExists(ResourceAlreadyExistsException ex){
        return buildErrorResponse(ex, HttpStatus.CONFLICT, "Already exists");
    }
    public ResponseEntity<ErrorResponseDTO> resourceNotFound(ResourceNotFoundException ex){
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, " Not Found");
    }

    public ResponseEntity<ErrorResponseDTO> handleInvalidCredentials(InvalidCredentialsException ex){
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, "Invalid Credentials");
    }

    public ResponseEntity<ErrorResponseDTO> handleFileStorage(FileStorageException ex) {

        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Cannot Save Image");
    }

}
