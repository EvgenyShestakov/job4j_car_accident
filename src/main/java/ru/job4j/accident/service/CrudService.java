package ru.job4j.accident.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accident.model.*;
import ru.job4j.accident.repository.*;

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

    private final AuthorityRepository authRep;

    private final UserRepository userRep;

    private final PasswordEncoder encoder;

    public CrudService(AccidentCrudRepository acRep,
                       AccidentTypeCrudRepository actRep,
                       RuleCrudRepository rulRep, AuthorityRepository authRep,
                       UserRepository userRep, PasswordEncoder encoder) {
        this.acRep = acRep;
        this.actRep = actRep;
        this.rulRep = rulRep;
        this.authRep = authRep;
        this.userRep = userRep;
        this.encoder = encoder;
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
    public void save(User user) {
        userRep.save(user);
    }

    @Override
    public Accident findAccidentById(int id) {
        return acRep.findById(id).get();
    }

    @Override
    public Rule findRuleById(int id) {
        return rulRep.findById(id).get();
    }

    @Override
    public Authority findByAuthority(String authority) {
        return authRep.findByAuthority(authority);
    }

    @Override
    public User findUserByName(String name) {
        return userRep.findByUsername(name);
    }

    @Override
    public PasswordEncoder getEncoder() {
        return encoder;
    }
}
