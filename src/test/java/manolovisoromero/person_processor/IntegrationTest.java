package manolovisoromero.person_processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import manolovisoromero.person_processor.dto.PersonDto;
import manolovisoromero.person_processor.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegrationTest {
    public static final String API_V_1_PERSONS = "/api/v1/persons";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private PersistAdapter<Person> persistAdapter;

    @BeforeEach
    void resetStore() {
//        persistAdapter.connect();
    }

    @Test
    void shouldProcessPersonViaControllerAndReturnValidResult() throws Exception {
        PersonDto parent1 = PersonDto.builder()
                .id(5L)
                .name("Jan")
                .partnerId(4L)
                .dateOfBirth(LocalDate.of(1970, 1, 1))
                .childrenIds(Set.of(1L, 2L, 3L))
                .build();
        postPerson(parent1);

        PersonDto partner = PersonDto.builder()
                .id(4L)
                .name("Willem")
                .partnerId(5L)
                .dateOfBirth(LocalDate.of(1980, 3, 3))
                .childrenIds(Set.of(1L, 2L, 3L))
                .build();

        postPerson(partner);

        PersonDto child1 = PersonDto.builder()
                .id(1L)
                .name("Arjan")
                .dateOfBirth(LocalDate.of(2000, 2, 2))
                .parentIds(Set.of(5L, 4L))
                .partnerId(4L)
                .childrenIds(Set.of(2L, 3L))
                .build();

        PersonDto child2 = PersonDto.builder()
                .id(2L)
                .name("Eric")
                .dateOfBirth(LocalDate.of(2020, 5, 5))
                .parentIds(Set.of(5L, 4L))
                .build();

        PersonDto child3 = PersonDto.builder()
                .id(3L)
                .name("Jeroen")
                .dateOfBirth(LocalDate.of(2022, 6, 6))
                .parentIds(Set.of(5L, 4L))
                .build();
        postPerson(child1);
        postPerson(child2);



        mockMvc.perform(post(API_V_1_PERSONS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(child3)))
                .andExpect(status().isOk())
                .andExpect(content().string("Currently 1 or more stored people match the criteria."));
    }

    private void postPerson(PersonDto dto) throws Exception {
        mockMvc.perform(post(API_V_1_PERSONS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }


}
