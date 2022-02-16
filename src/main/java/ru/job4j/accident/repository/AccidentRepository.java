package ru.job4j.accident.repository;

import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;

import java.util.Collection;

public interface AccidentRepository {
    Collection<Accident> findAllAccidents();

    Collection<AccidentType> findAllAccidentTypes();

    void create(Accident accident);

    Accident findAccidentById(int id);
}
