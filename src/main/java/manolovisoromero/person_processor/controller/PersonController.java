package manolovisoromero.person_processor.controller;

import lombok.RequiredArgsConstructor;
import manolovisoromero.person_processor.dto.PersonDto;
import manolovisoromero.person_processor.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    public static final String MATCH_THE_CRITERIA = "Currently 1 or more stored people match the criteria.";
    public static final String NO_ONE_MATCHES_YET = "No one matches yet.";
    private final PersonService personService;


    @PostMapping("/persons")
    public ResponseEntity<String> upsertPerson(@RequestBody PersonDto personDto) {
        var result = personService.processPerson(personDto);
        if(result.satisfied()){
            return ResponseEntity.status(HttpStatus.valueOf(200)).body(MATCH_THE_CRITERIA);
        }
        return ResponseEntity.status(HttpStatus.valueOf(444)).body(NO_ONE_MATCHES_YET);
    }
}
