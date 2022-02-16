package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentRepository;

import java.util.Collection;

@Service
public class AccidentServiceImpl implements AccidentService {
    private AccidentRepository repository;

    public AccidentServiceImpl(AccidentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Accident> findAll() {
        return repository.findAll();
    }
}
