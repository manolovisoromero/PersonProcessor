package manolovisoromero.person_processor.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import manolovisoromero.person_processor.dto.PersonDto;
import manolovisoromero.person_processor.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/persons")
@RequiredArgsConstructor
public class PersonController {

    public static final String MATCH_THE_CRITERIA = "Currently 1 or more stored people match the criteria.";
    public static final String NO_ONE_MATCHES_YET = "No one matches yet.";
    private final PersonService personService;
    private static final Logger log = LoggerFactory.getLogger(PersonController.class);

    @PostMapping
    public ResponseEntity<String> upsertPerson(@Valid @RequestBody PersonDto personDto) {
        log.info("Received upsert request for person with id: {}", personDto.getId());
        var result = personService.processPerson(personDto);
        if(result.satisfied()){
            return ResponseEntity.ok().body(MATCH_THE_CRITERIA);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(NO_ONE_MATCHES_YET);
    }
}
