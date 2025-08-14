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

    private final PersonService personService;


    @PostMapping("/persons")
    public ResponseEntity<Boolean> upsertPerson(@RequestBody PersonDto personDto) {
        var result = personService.processPerson(personDto);
        if(result.satisfied()){
            return ResponseEntity.status(HttpStatus.valueOf(200)).body(result.satisfied());
        }
        return ResponseEntity.status(HttpStatus.valueOf(444)).body(result.satisfied());
    }
}
