package ru.job4j.accident.repository;

import ru.job4j.accident.model.Accident;

import java.util.Collection;

public interface AccidentRepository {
    Collection<Accident> findAll();
}
