package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem implements AccidentRepository {
    private static final AtomicInteger ACCIDENT_ID = new AtomicInteger(4);
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>();
    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    public AccidentMem() {
        types.put(1, AccidentType.of(1, "Две машины"));
        types.put(2, AccidentType.of(2, "Машина и человек"));
        types.put(3, AccidentType.of(3, "Машина и велосипед"));
        rules.put(1, Rule.of(1, "Статья. 1"));
        rules.put(2, Rule.of(2, "Статья. 2"));
        rules.put(3, Rule.of(3, "Статья. 3"));
        accidents.put(1, Accident.of(1, "название1", "описание1",
                "адрес1", types.get(1), new HashSet<>(Arrays.asList(rules.get(1), rules.get(2)))));
        accidents.put(2, Accident.of(2, "название2", "описание2",
                "адрес2", types.get(2), new HashSet<>(Arrays.asList(rules.get(1), rules.get(3)))));
        accidents.put(3, Accident.of(3, "название3", "описание3",
                "адрес3", types.get(3), new HashSet<>(Arrays.asList(rules.get(2), rules.get(3)))));
    }

    @Override
    public Collection<Accident> findAllAccidents() {
        return accidents.values();
    }

    @Override
    public Collection<AccidentType> findAllAccidentTypes() {
        return types.values();
    }

    @Override
    public Collection<Rule> findAllRules() {
        return rules.values();
    }

    @Override
    public void save(Accident accident, String[] ids) {
        if (accident.getId() == 0) {
            int id = ACCIDENT_ID.incrementAndGet();
            accident.setId(id);
            addTypeName(accident);
            accident.setRules(addRules(ids));
            accidents.put(id, accident);
        } else {
            addTypeName(accident);
            accident.setRules(addRules(ids));
            accidents.put(accident.getId(), accident);
        }
    }

    private void addTypeName(Accident accident) {
        AccidentType type = accident.getType();
        String typeName = types.get(type.getId()).getName();
        type.setName(typeName);
    }

    private Set<Rule> addRules(String[] ids) {
        Set<Rule> set = new HashSet<>();
        for (String id: ids) {
            set.add(rules.get(Integer.parseInt(id)));
        }
        return set;
    }

    @Override
    public Accident findAccidentById(int id) {
        return accidents.get(id);
    }

    @Override
    public Rule findRuleById(int id) {
        return null;
    }
}
