package ru.job4j.accident.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.Optional;

@Repository
public interface AccidentCrudRepository extends CrudRepository<Accident, Integer> {
    @Override
    @EntityGraph(attributePaths = {"type", "rules"})
    Iterable<Accident> findAll();

    @Override
    @EntityGraph(attributePaths = {"type", "rules"})
    Optional<Accident> findById(Integer id);
}
