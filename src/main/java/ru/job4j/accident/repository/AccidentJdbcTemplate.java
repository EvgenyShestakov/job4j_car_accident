package ru.job4j.accident.repository;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.sql.PreparedStatement;
import java.util.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccidentJdbcTemplate implements AccidentRepository {
    private final JdbcTemplate jdbc;

    public AccidentJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Override
    public Collection<Accident> findAllAccidents() {
        String query = "select ac.id as ac_id, ac.name as ac_name,"
                + " ac.text as ac_text, ac.address as ac_address, act.id as act_id,"
                + " act.name as act_name,"
                + "rule.id as rule_id, rule.name as rule_name from accident ac join"
                + " accident_type act on "
                + "ac.accident_type_id = act.id join accident_rule on "
                + "accident_rule.accident_id = ac.id join rule on "
                + "accident_rule.rule_id = rule.id";
    return selectAccident(query);
    }

    private Collection<Accident> selectAccident(String string) {
        Map<Integer, Accident> accidents = new HashMap<>();
        jdbc.query(string,
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("rule_id"));
                    rule.setName(rs.getString("rule_name"));
                    int acId = rs.getInt("ac_id");
                    Accident accident = null;
                    if (accidents.containsKey(acId)) {
                        accidents.get(acId).addRule(rule);
                    } else {
                        accident = new Accident();
                        accident.setRules(new HashSet<>());
                        AccidentType accidentType = new AccidentType();
                        accident.setId(acId);
                        accident.setName(rs.getString("ac_name"));
                        accident.setText(rs.getString("ac_text"));
                        accident.setAddress(rs.getString("ac_address"));
                        accidentType.setId(rs.getInt("act_id"));
                        accidentType.setName(rs.getString("act_name"));
                        accident.setType(accidentType);
                        accident.addRule(rule);
                        accidents.put(acId, accident);
                    }
                    return accident;
                });
        return accidents.values();
    }

    @Override
    public Collection<AccidentType> findAllAccidentTypes() {
        return jdbc.query("select id, name from accident_type",
                (rs, row) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("name"));
                    return accidentType;
                });
    }

    @Override
    public Collection<Rule> findAllRules() {
        return jdbc.query("select id, name from rule",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
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
        KeyHolder keyHolder = new GeneratedKeyHolder();
       jdbc.update(con -> {
                   PreparedStatement ps = con.prepareStatement("insert into"
                           + " accident(name, text, address, accident_type_id)"
                           + " values ((?), (?), (?), (?))", new String[] {"id"});
       ps.setString(1, accident.getName());
       ps.setString(2, accident.getText());
       ps.setString(3, accident.getAddress());
       ps.setInt(4, accident.getType().getId());
       return ps;
       }, keyHolder);
        accident.setId(keyHolder.getKey().intValue());
        insertLinkTable(accident);
    }

    private void update(Accident accident) {
        jdbc.update("update accident set name = (?), text = (?), address = (?),"
                        + " accident_type_id = (?) where id = (?)", accident.getName(),
                accident.getText(), accident.getAddress(), accident.getType().getId(),
                accident.getId());
        jdbc.update("delete from accident_rule where accident_id = (?)", accident.getId());
        insertLinkTable(accident);

    }

    private void insertLinkTable(Accident accident) {
        for (Rule rule : accident.getRules()) {
            jdbc.update("insert into accident_rule(accident_id, rule_id)"
                    + "values ((?), (?))", accident.getId(), rule.getId());
        }
    }

    private Set<Rule> addRules(String[] ids) {
        Set<Rule> set = new HashSet<>();
        for (String id: ids) {
            set.add(findRuleById(Integer.parseInt(id)));
        }
        return set;
    }

    @Override
    public Accident findAccidentById(int id) {
        String query = String.format("select ac.id as ac_id, ac.name as ac_name,"
                + " ac.text as ac_text, ac.address as ac_address, act.id as act_id,"
                + " act.name as act_name,"
                + "rule.id as rule_id, rule.name as rule_name from accident ac join"
                + " accident_type act on "
                + "ac.accident_type_id = act.id join accident_rule on "
                + "accident_rule.accident_id = ac.id join rule on "
                + "accident_rule.rule_id = rule.id where ac.id=%d", id);
        return selectAccident(query).stream().findAny().get();
    }

    @Override
    public Rule findRuleById(int id) {
        String query = String.format("select id, name from rule where id=%d", id);
        return jdbc.query(query,
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                }).stream().findAny().get();
    }
}
