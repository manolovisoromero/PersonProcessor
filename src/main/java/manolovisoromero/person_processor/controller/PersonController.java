package manolovisoromero.person_processor.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import manolovisoromero.person_processor.dto.DeleteRequestDto;
import manolovisoromero.person_processor.dto.PersonDto;
import manolovisoromero.person_processor.service.CheckResult;
import manolovisoromero.person_processor.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;
    private static final Logger log = LoggerFactory.getLogger(PersonController.class);

    @PostMapping
    public ResponseEntity<String> upsertPerson(@Valid @RequestBody PersonDto dto) {
        log.info("Received upsert request for person with id: {}", dto.getId());
        final CheckResult result = personService.processPerson(dto);
        if (result.matchingPerson().isEmpty()) {
            log.info("Criteria not met after processing person with id {}", dto.getId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.message());
        }
        log.info("Criteria met after processing person with id {}", result.matchingPerson().get().getId());
        return ResponseEntity.ok().body(result.message());
    }

    @DeleteMapping
    public ResponseEntity<String> deletePerson(@Valid @RequestBody DeleteRequestDto dto){
        log.info("Received delete request for persons with id's: {}", dto.getIds());
        //Not implemented yet
        return null;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errors);
    }
}
