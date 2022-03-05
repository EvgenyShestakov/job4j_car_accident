package ru.job4j.accident.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.job4j.accident.model.*;

import java.util.Collection;

public interface AccidentService {
    Iterable<Accident> findAllAccidents();

    Collection<AccidentType> findAllAccidentTypes();

    Collection<Rule> findAllRules();

    void save(Accident accident, String[] ids);

    void save(User user);

    Accident findAccidentById(int id);

    Rule findRuleById(int id);

    Authority findByAuthority(String authority);

    User findUserByName(String name);

    PasswordEncoder getEncoder();
}
