package org.fantabid.model;

import static org.fantabid.generated.Tables.*;
import static org.jooq.impl.DSL.*;

import java.sql.Date;
import java.util.Optional;
import java.util.stream.Stream;

import org.fantabid.Main;
import org.fantabid.generated.tables.records.AllenatoreRecord;
import org.fantabid.generated.tables.records.CalciatoreRecord;
import org.fantabid.generated.tables.records.CampionatoRecord;
import org.fantabid.generated.tables.records.PuntataRecord;
import org.fantabid.generated.tables.records.RegolaRecord;
import org.fantabid.generated.tables.records.SquadraRecord;
import org.fantabid.model.utils.Pair;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public final class Queries {
    
    public static final DSLContext query = DSL.using(Main.getConnection(), SQLDialect.POSTGRES);
    
    private Queries() { }
    
    public static AllenatoreRecord registerUser(String name, String surname, String username, String password) {
        AllenatoreRecord user = new AllenatoreRecord();
        user.setUsername(username);
        user.setPassword(password);
        user.setNome(name);
        user.setCognome(surname);
        query.insertInto(ALLENATORE).values(user.intoArray()).execute();
        return user;
    }
    
    public static CampionatoRecord registerLeague(String leagueName, String description, int budget, Date opening,
                                                  Date closure, boolean isBid, int maxTeam) {
        CampionatoRecord league = new CampionatoRecord();
        league.setIdcampionato(getLastLeagueId().orElse(-1) + 1);
        league.setNomecampionato(leagueName);
        league.setBudgetpersquadra((short) budget);
        league.setDataapertura(opening);
        league.setDatachiusura(closure);
        league.setAstarialzo(String.valueOf(isBid));
        Optional.ofNullable((byte) maxTeam).filter(m -> isBid).ifPresent(league::setNumeromassimosquadre);        
        query.insertInto(CAMPIONATO).values(league.intoArray()).execute();
        return league;
    }
    
    public static SquadraRecord registerTeam(int leagueId, String username, String teamName, int budget) {
        SquadraRecord team = new SquadraRecord();
        team.setIdsquadra(getLastTeamId().orElse(-1) + 1);
        team.setIdcampionato(leagueId);
        team.setUsername(username);
        team.setNomesquadra(teamName);
        team.setCreditoresiduo((short) budget);
        query.insertInto(SQUADRA).values(team.intoArray()).execute();
        return team;
    }
    
    public static void linkRuleToLeague(int ruleId, int leagueId) {
        query.insertInto(REGOLE_PER_CAMPIONATO)
             .values(ruleId, leagueId)
             .execute();
    }
    
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
    
    public static Optional<AllenatoreRecord> getUser(String username) {
        return query.select()
                    .from(ALLENATORE)
                    .where(ALLENATORE.USERNAME.eq(username))
                    .stream()
                    .map(r -> (AllenatoreRecord) r)
                    .findFirst();
    }
    
    public static Optional<CampionatoRecord> getLeague(int leagueId) {
        return query.select()
                    .from(CAMPIONATO)
                    .where(CAMPIONATO.IDCAMPIONATO.eq(leagueId))
                    .stream()
                    .map(r -> (CampionatoRecord) r)
                    .findFirst();
    }
    
    public static Optional<SquadraRecord> getTeam(int teamId) {
        return query.select()
                    .from(SQUADRA)
                    .where(SQUADRA.IDSQUADRA.eq(teamId))
                    .stream()
                    .map(r -> (SquadraRecord) r)
                    .findFirst();
    }
    
    public static Optional<CalciatoreRecord> getPlayer(int playerId) {        
        return query.select()
                    .from(CALCIATORE)
                    .where(CALCIATORE.IDCALCIATORE.eq((short) playerId))
                    .stream()
                    .map(r -> (CalciatoreRecord) r)
                    .findFirst();
    }
    
    public static Stream<AllenatoreRecord> getAllUsers() {
        return query.select()
                    .from(ALLENATORE)
                    .fetch()
                    .stream()
                    .map(r -> (AllenatoreRecord) r);
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

    public static Stream<SquadraRecord> getAllFantabidTeams() {
        return query.select()
                    .from(SQUADRA)
                    .fetch()
                    .stream()
                    .map(r -> (SquadraRecord) r);
    }
    
    public static Stream<String> getAllRealTeams() {
        return query.selectDistinct(CALCIATORE.SQUADRA)
                    .from(CALCIATORE)
                    .fetch()
                    .stream()
                    .map(r -> r.value1())
                    .sorted();
    }
    
    public static Stream<CalciatoreRecord> getAllPlayers() {
        return query.select()
                    .from(CALCIATORE)
                    .fetch()
                    .stream()
                    .map(r -> (CalciatoreRecord) r);
    }

    public static Stream<RegolaRecord> getAllRules() {
        return query.select()
                    .from(REGOLA)
                    .fetch()
                    .stream()
                    .map(r -> (RegolaRecord) r);
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
    
    public static Stream<Pair<SquadraRecord, CampionatoRecord>> getTeamsFromUser(String user) {
        return query.select()
                    .from(SQUADRA)
                    .join(CAMPIONATO)
                    .on(SQUADRA.IDCAMPIONATO.eq(CAMPIONATO.IDCAMPIONATO))
                    .where(SQUADRA.USERNAME.eq(user))
                    .orderBy(CAMPIONATO.DATACHIUSURA)
                    .fetch()
                    .stream()
                    .map(r -> Pair.of(r.into(SQUADRA), r.into(CAMPIONATO)));
                    
    }
    
    public static Optional<PuntataRecord> getLastBet(int leagueId, int playerId) {
        return query.select()
                    .from(PUNTATA)
                    .join(SQUADRA)
                    .on(PUNTATA.IDSQUADRA.eq(SQUADRA.IDSQUADRA))
                    .where(SQUADRA.IDCAMPIONATO.eq(leagueId))
                    .and(PUNTATA.IDCALCIATORE.eq((short) playerId))
                    .and(PUNTATA.PUNTATASUCCESSIVA.isNull())
                    .fetch()
                    .stream()
                    .map(r -> (PuntataRecord) r)
                    .findFirst();
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
    public static void testQuery(Object ...args) {
//        PUNTATA.fieldStream().forEach(System.out::println);
    }
}
