package manolovisoromero.person_processor.controller;

import manolovisoromero.person_processor.dto.PersonDto;
import manolovisoromero.person_processor.service.CheckResult;
import manolovisoromero.person_processor.service.CheckResultImpl;
import manolovisoromero.person_processor.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    void shouldReturnCheckResultWhenValidDtoIsPosted() throws Exception {
        String json = """
                    {
                      "id": 1,
                      "name": "Jan",
                      "dateOfBirth": "2000-02-02",
                      "parentIds": [2, 3],
                      "partnerId": 4,
                      "childrenIds": [5]
                    }
                """;

        CheckResult result = CheckResultImpl.builder().satisfied(true).build();
        when(personService.processPerson(any(PersonDto.class))).thenReturn(result);

        mockMvc.perform(post("/api/v1/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnBadRequestWhenIdIsMissing() throws Exception {
        String json = """
                    {
                      "name": "Jan"
                    }
                """;

        mockMvc.perform(post("/api/v1/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

}

