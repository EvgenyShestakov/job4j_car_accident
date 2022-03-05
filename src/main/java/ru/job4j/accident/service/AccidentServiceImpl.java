package ru.job4j.accident.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.accident.model.*;
import ru.job4j.accident.repository.AccidentRepository;
import java.util.Collection;

@Service
public class AccidentServiceImpl implements AccidentService {
    private final AccidentRepository repository;

    public AccidentServiceImpl(@Qualifier("accidentJdbcTemplate")AccidentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Iterable<Accident> findAllAccidents() {
        return repository.findAllAccidents();
    }

    @Override
    public Collection<AccidentType> findAllAccidentTypes() {
        return repository.findAllAccidentTypes();
    }

    @Override
    public Collection<Rule> findAllRules() {
        return repository.findAllRules();
    }

    @Override
    public void save(Accident accident, String[] ids) {
        repository.save(accident, ids);
    }

    @Override
    public void save(User user) {

    }

    @Override
    public Accident findAccidentById(int id) {
        return repository.findAccidentById(id);
    }

    @Override
    public Rule findRuleById(int id) {
        return repository.findRuleById(id);
    }

    @Override
    public Authority findByAuthority(String authority) {
        return null;
    }

    @Override
    public User findUserByName(String name) {
        return null;
    }

    @Override
    public PasswordEncoder getEncoder() {
        return null;
    }
}
