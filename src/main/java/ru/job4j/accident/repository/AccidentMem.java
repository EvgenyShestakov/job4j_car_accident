package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.Collection;
import java.util.HashMap;

@Repository
public class AccidentMem implements AccidentRepository {
    private final HashMap<Integer, Accident> accidents = new HashMap<>();
    private int index = 4;

    {
          accidents.put(1, new Accident(1, "имя1", "текст1", "адрес1"));
          accidents.put(2, new Accident(2, "имя2", "текст2", "адрес2"));
          accidents.put(3, new Accident(3, "имя3", "текст3", "адрес3"));
    }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();
    }

    @Override
    public void create(Accident accident) {
        if (accident.getId() == 0) {
            int id = index++;
            accident.setId(id);
            accidents.put(id, accident);
        } else {
            accidents.put(accident.getId(), accident);
        }
    }
}
