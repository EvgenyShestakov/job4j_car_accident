package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentRepository;
import java.util.Collection;

@Service
public class AccidentServiceImpl implements AccidentService {
    private final AccidentRepository repository;

    public AccidentServiceImpl(AccidentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Accident> findAllAccidents() {
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
    public void create(Accident accident, String[] ids) {
        repository.create(accident, ids);
    }

    @Override
    public Accident findAccidentById(int id) {
        return repository.findAccidentById(id);
    }
}
