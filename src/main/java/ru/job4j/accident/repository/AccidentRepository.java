package ru.job4j.accident.repository;

import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import java.util.Collection;

public interface AccidentRepository {
    Collection<Accident> findAllAccidents();

    Collection<AccidentType> findAllAccidentTypes();

    Collection<Rule> findAllRules();

    void save(Accident accident, String[] ids);

    Accident findAccidentById(int id);

    Rule findRuleById(int id);
}
