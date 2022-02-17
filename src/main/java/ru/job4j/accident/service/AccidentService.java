package ru.job4j.accident.service;

import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.Collection;
import java.util.Set;

public interface AccidentService {
    Collection<Accident> findAllAccidents();

    Collection<AccidentType> findAllAccidentTypes();

    Collection<Rule> findAllRules();

    void create(Accident accident, String[] ids);

    Accident findAccidentById(int id);
}
