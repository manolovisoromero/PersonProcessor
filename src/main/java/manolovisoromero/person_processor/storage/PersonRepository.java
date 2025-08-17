package manolovisoromero.person_processor.storage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Long>, PersonRepositoryCustom {
}
