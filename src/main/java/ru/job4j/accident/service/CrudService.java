package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentCrudRepository;
import ru.job4j.accident.repository.AccidentTypeCrudRepository;
import ru.job4j.accident.repository.RuleCrudRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class CrudService implements AccidentService {
    private final AccidentCrudRepository acRep;

    private final AccidentTypeCrudRepository actRep;

    private final RuleCrudRepository rulRep;

    public CrudService(AccidentCrudRepository acRep,
                       AccidentTypeCrudRepository actRep,
                       RuleCrudRepository rulRep) {
        this.acRep = acRep;
        this.actRep = actRep;
        this.rulRep = rulRep;
    }

    @Override
    public Collection<Accident> findAllAccidents() {
        List<Accident> res = new ArrayList<>();
        acRep.findAll().forEach(res::add);
        return res;
    }

    @Override
    public Collection<AccidentType> findAllAccidentTypes() {
        List<AccidentType> res = new ArrayList<>();
        actRep.findAll().forEach(res::add);
        return res;
    }

    @Override
    public Collection<Rule> findAllRules() {
        List<Rule> res = new ArrayList<>();
        rulRep.findAll().forEach(res::add);
        return res;
    }

    @Override
    public void save(Accident accident, String[] ids) {
        accident.setRules(new HashSet<>());
        for (String s : ids) {
            accident.addRule(rulRep.findById(Integer.parseInt(s)).get());
        }
        acRep.save(accident);
    }

    @Override
    public Accident findAccidentById(int id) {
        return acRep.findById(id).get();
    }

    @Override
    public Rule findRuleById(int id) {
        return rulRep.findById(id).get();
    }
}
