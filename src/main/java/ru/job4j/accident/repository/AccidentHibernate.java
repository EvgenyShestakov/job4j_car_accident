package ru.job4j.accident.repository;

import org.hibernate.Transaction;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import java.util.Collection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

@Repository
public class AccidentHibernate implements AccidentRepository {
    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    private void txv(final Consumer<Session> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            command.accept(session);
            tx.commit();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public Collection<Accident> findAllAccidents() {
        return tx(session -> session.createQuery("select distinct a from Accident a join fetch a."
                        + "rules", Accident.class).list());
    }

    @Override
    public Collection<AccidentType> findAllAccidentTypes() {
        return tx(session -> session.createQuery("from ru.job4j.accident.model.AccidentType").
                list());
    }

    @Override
    public Collection<Rule> findAllRules() {
        return tx(session -> session.createQuery("from ru.job4j.accident.model.Rule").list());
    }

    @Override
    public void save(Accident accident, String[] ids) {
        accident.setRules(addRules(ids));
        if (accident.getId() == 0) {
            create(accident);
        } else {
            update(accident);
        }
    }

    private void create(Accident accident) {
        txv(session -> session.save(accident));
    }

    private void update(Accident accident) {
        txv(session -> session.update(accident));
    }

    private Set<Rule> addRules(String[] ids) {
        return tx(session -> {
            Set<Rule> set = new HashSet<>();
            for (String id : ids) {
                Rule rule = session.find(Rule.class, Integer.parseInt(id));
                set.add(rule);
            }
            return set;
        });
    }

    @Override
    public Accident findAccidentById(int id) {
        return tx(session -> session.find(Accident.class, id));
    }

    @Override
    public Rule findRuleById(int id) {
        return tx(session -> session.find(Rule.class, id));
    }
}
