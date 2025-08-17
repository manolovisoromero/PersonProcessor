package manolovisoromero.person_processor.storage;

import manolovisoromero.person_processor.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    // Extending JpaRepository gives me all the queries I need: save, findAll and delete, no need to add custom queries.
}
