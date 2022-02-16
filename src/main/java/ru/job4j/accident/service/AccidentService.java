package ru.job4j.accident.service;

import ru.job4j.accident.model.Accident;

import java.util.Collection;

public interface AccidentService {
    Collection<Accident> findAll();
}
