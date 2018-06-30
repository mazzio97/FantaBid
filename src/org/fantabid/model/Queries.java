package org.fantabid.model;

import static org.fantabid.generated.Tables.*;

import java.sql.Date;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.fantabid.Main;
import org.fantabid.controller.LeagueController;
import org.fantabid.entities.Player;
import org.fantabid.generated.tables.records.RegolaRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public final class Queries {
    
    public static final DSLContext query = DSL.using(Main.getConnection(), SQLDialect.POSTGRES);
    
    private Queries() { }
    
    public static boolean checkUsername(String username) {
        return query.select(ALLENATORE.USERNAME)
                    .from(ALLENATORE)
                    .where(ALLENATORE.USERNAME.eq(username))
                    .fetch()
                    .stream()
                    .findFirst()
                    .isPresent();
    }
    
    public static boolean checkPassword(String username, String password) {
        return query.select(ALLENATORE.PASSWORD)
                    .from(ALLENATORE)
                    .where(ALLENATORE.USERNAME.eq(username))
                    .fetch()
                    .stream()
                    .map(r -> r.getValue(0))
                    .peek(System.out::println)
                    .findFirst()
                    .filter(password::equals)
                    .isPresent();
    }
    
    public static void registerUser(String name, String surname, String user, String password) {
        query.insertInto(ALLENATORE)
             .values(user, password, name, surname)
             .execute();
    }
    
    public static Stream<String> getLeagueFromUser(String user) {
        return query.select(CAMPIONATO.IDCAMPIONATO, SQUADRA.NOMESQUADRA, CAMPIONATO.DATACHIUSURA)
                    .from(SQUADRA.join(CAMPIONATO).on(CAMPIONATO.IDCAMPIONATO.eq(SQUADRA.IDCAMPIONATO)))
                    .where(SQUADRA.USERNAME.eq(user))
                    .orderBy(CAMPIONATO.DATACHIUSURA)
                    .fetch()
                    .stream()
                    .map(r -> r.field1() + " (" + r.field2() + "), closing: " + r.field3());
    }
    
    public static Stream<String> getOpenLeagues() {
        return query.select(CAMPIONATO.IDCAMPIONATO, CAMPIONATO.DATACHIUSURA)
                    .from(CAMPIONATO)
                    .where(CAMPIONATO.DATAAPERTURA.ge(new Date(System.currentTimeMillis())))
                    .and(CAMPIONATO.DATACHIUSURA.le(new Date(System.currentTimeMillis())))
                    .fetch()
                    .stream()
                    .map(r -> r.field1() + ", closing: " + r.field2());
    }

    public static Stream<RegolaRecord> getRules() {
        return query.select()
                    .from(REGOLA)
                    .fetch()
                    .stream()
                    .map(r -> (RegolaRecord) r);
    }

    public static Collection<String> getAllTeams() {
        return query.selectDistinct(CALCIATORE.SQUADRA)
                    .from(CALCIATORE)
                    .fetch()
                    .stream()
                    .map(r -> r.getValue(0).toString())
                    .sorted()
                    .collect(Collectors.toList());
    }
    
    public static Collection<Player> filterPlayers(String namePart, String role, String team) {
        return query.select()
                    .from(CALCIATORE)
                    .where(CALCIATORE.NOME.contains(namePart.toUpperCase()))
                    .and(CALCIATORE.RUOLO.eq(role).or(role.equals(LeagueController.ALL_AVAILABLE_OPTIONS)))
                    .and(CALCIATORE.SQUADRA.eq(team).or(team.equals(LeagueController.ALL_AVAILABLE_OPTIONS)))
                    .fetch()
                    .stream()
                    .map(r -> new Player(Integer.parseInt(r.getValue(0).toString()), r.getValue(1).toString(), r.getValue(4).toString(), r.getValue(2).toString(), Integer.parseInt(r.getValue(3).toString())))
                    .collect(Collectors.toList());
    }

    // TODO: TO BE REMOVED
    public static Result<?> testQuery(Object ...args) {
        return query.select()
                    .from(CAMPIONATO)
                    .fetch();
    }
}
