package org.fantabid.model;

import static org.fantabid.generated.Tables.*;
import static org.jooq.impl.DSL.*;

import java.sql.Date;
import java.util.Optional;
import java.util.stream.Stream;

import org.fantabid.Main;
import org.fantabid.generated.tables.records.CalciatoreRecord;
import org.fantabid.generated.tables.records.CampionatoRecord;
import org.fantabid.generated.tables.records.RegolaRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;

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
                    .findFirst()
                    .filter(password::equals)
                    .isPresent();
    }
    
    public static void registerUser(String name, String surname, String user, String password) {
        query.insertInto(ALLENATORE)
             .values(user, password, name, surname)
             .execute();
    }
    
    public static int registerLeague(String leagueName, String description, int budget, Date opening,
                                     Date closure, boolean isBid, int maxTeam) {
        int leagueId = getLastLeagueId().orElse(-1) + 1;
        query.insertInto(CAMPIONATO)
             .values(leagueId, leagueName, description, budget, opening, closure, isBid,
                     Optional.ofNullable(maxTeam).filter(m -> isBid).orElse(null))
             .execute();
        return leagueId;
    }
    
    public static int registerTeam(int leagueId, String username, String teamName, int budget) {
        int teamId = getLastTeamId().orElse(-1) + 1;
        query.insertInto(SQUADRA)
             .values(teamId, leagueId, username, teamName, budget)
             .execute();
        return teamId;
    }
    
    public static void linkRuleToLeague(int ruleId, int leagueId) {
        query.insertInto(REGOLE_PER_CAMPIONATO)
             .values(ruleId, leagueId)
             .execute();
    }
    
    public static Stream<RegolaRecord> getRulesFromLeague(int leagueId) {
        return query.select(REGOLE_PER_CAMPIONATO.asterisk())
                    .from(REGOLE_PER_CAMPIONATO)
                    .join(CAMPIONATO)
                    .on(REGOLE_PER_CAMPIONATO.IDCAMPIONATO.eq(CAMPIONATO.IDCAMPIONATO))
                    .fetch()
                    .stream()
                    .map(r -> (RegolaRecord) r);
    }
    
    public static Stream<Record> getTeamsFromUser(String user) {
        return query.select()
                    .from(SQUADRA)
                    .join(CAMPIONATO)
                    .on(SQUADRA.IDCAMPIONATO.eq(CAMPIONATO.IDCAMPIONATO))
                    .where(SQUADRA.USERNAME.eq(user))
                    .orderBy(CAMPIONATO.DATACHIUSURA)
                    .fetch()
                    .stream();
    }
    
    public static Stream<CampionatoRecord> getOpenLeagues() {
        return query.select()
                    .from(CAMPIONATO)
                    .where(CAMPIONATO.DATAAPERTURA.ge(new Date(System.currentTimeMillis())))
                    .and(CAMPIONATO.DATACHIUSURA.le(new Date(System.currentTimeMillis())))
                    .fetch()
                    .stream()
                    .map(r -> (CampionatoRecord) r);
    }
    
    public static Optional<CampionatoRecord> getLeagueInfo(int leagueId) {
        return query.select()
                    .from(CAMPIONATO)
                    .where(CAMPIONATO.IDCAMPIONATO.eq(leagueId))
                    .stream()
                    .map(r -> (CampionatoRecord) r)
                    .findFirst();
    }

    public static Stream<RegolaRecord> getRules() {
        return query.select()
                    .from(REGOLA)
                    .fetch()
                    .stream()
                    .map(r -> (RegolaRecord) r);
    }

    public static Stream<String> getAllTeams() {
        return query.selectDistinct(CALCIATORE.SQUADRA)
                    .from(CALCIATORE)
                    .fetch()
                    .stream()
                    .map(r -> r.value1())
                    .sorted();
    }
    
    public static Stream<CalciatoreRecord> filterPlayers(String namePart, String role, String team) {
        SelectConditionStep<?> s = query.select()
                                        .from(CALCIATORE)
                                        .where(CALCIATORE.NOME.contains(namePart.toUpperCase()));
        Optional.ofNullable(role).ifPresent(r -> s.and(CALCIATORE.RUOLO.eq(r)));
        Optional.ofNullable(team).ifPresent(t -> s.and(CALCIATORE.SQUADRA.eq(t)));
        return s.fetch().stream().map(r -> (CalciatoreRecord) r);
    }
    
    public static Optional<Integer> getLastLeagueId() {
        return query.select(max(CAMPIONATO.IDCAMPIONATO))
                    .from(CAMPIONATO)
                    .fetch()
                    .stream()
                    .map(Record1::value1)
                    .findFirst();
    }
    
    public static Optional<Integer> getLastTeamId() {
        return query.select(max(SQUADRA.IDSQUADRA))
                    .from(SQUADRA)
                    .fetch()
                    .stream()
                    .map(Record1::value1)
                    .findFirst();
    }

    // TODO: TO BE REMOVED
    public static Result<?> testQuery(Object ...args) {
        return query.select()
                    .from(CAMPIONATO)
                    .fetch();
    }
}
