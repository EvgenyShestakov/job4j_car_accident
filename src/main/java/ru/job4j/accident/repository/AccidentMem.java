package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;

import java.util.Collection;
import java.util.HashMap;

@Repository
public class AccidentMem implements AccidentRepository {
    private final HashMap<Integer, Accident> accidents = new HashMap<>();
    private final HashMap<Integer, AccidentType> types = new HashMap<>();
    private int index = 4;

    {
        types.put(1, AccidentType.of(1, "Две машины"));
        types.put(2, AccidentType.of(2, "Машина и человек"));
        types.put(3, AccidentType.of(3, "Машина и велосипед"));
        accidents.put(1, new Accident(1, "название1", "описание1", "адрес1", types.get(1)));
        accidents.put(2, new Accident(2, "название2", "описание2", "адрес2", types.get(2)));
        accidents.put(3, new Accident(3, "название3", "описание3", "адрес3", types.get(3)));
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
    public void create(Accident accident) {
        if (accident.getId() == 0) {
            int id = index++;
            accident.setId(id);
            addTypeName(accident);
            accidents.put(id, accident);
        } else {
            addTypeName(accident);
            accidents.put(accident.getId(), accident);
        }
    }

    private void addTypeName(Accident accident) {
        AccidentType type = accident.getType();
        String typeName = types.get(type.getId()).getName();
        type.setName(typeName);
    }

    @Override
    public Accident findAccidentById(int id) {
        return accidents.get(id);
    }
}
