package manolovisoromero.person_processor.storage;

import manolovisoromero.person_processor.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Long>, PersonRepositoryCustom {
}
