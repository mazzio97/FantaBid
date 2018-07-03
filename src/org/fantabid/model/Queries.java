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
                                                  Date closure, boolean isBid, Byte maxTeams) {
        CampionatoRecord league = new CampionatoRecord();
        league.setIdcampionato(getLastLeagueId().orElse(-1) + 1);
        league.setNomecampionato(leagueName);
        league.setDescrizione(description);
        league.setBudgetpersquadra((short) budget);
        league.setDataapertura(opening);
        league.setDatachiusura(closure);
        league.setAstarialzo(isBid);
        league.setNumeromassimosquadre(maxTeams);
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
                .filter(Queries::filterNullValues)
                .findFirst()
                .isPresent();
    }
    
    public static boolean checkPassword(String username, String password) {
        return query.select(ALLENATORE.PASSWORD)
                .from(ALLENATORE)
                .where(ALLENATORE.USERNAME.eq(username))
                .fetch()
                .stream()
                .map(Record1::value1)
                .filter(Queries::filterNullValues)
                .filter(password::equals)
                .findFirst()
                .isPresent();
    }
    
    public static Optional<AllenatoreRecord> getUser(String username) {
        return query.select()
                    .from(ALLENATORE)
                    .where(ALLENATORE.USERNAME.eq(username))
                    .stream()
                    .filter(Queries::filterNullValues)
                    .map(r -> (AllenatoreRecord) r)
                    .findFirst();
    }
    
    public static Optional<CampionatoRecord> getLeague(int leagueId) {
        return query.select()
                    .from(CAMPIONATO)
                    .where(CAMPIONATO.IDCAMPIONATO.eq(leagueId))
                    .stream()
                    .filter(Queries::filterNullValues)
                    .map(r -> (CampionatoRecord) r)
                    .findFirst();
    }
    
    public static Optional<SquadraRecord> getTeam(int teamId) {
        return query.select()
                    .from(SQUADRA)
                    .where(SQUADRA.IDSQUADRA.eq(teamId))
                    .stream()
                    .filter(Queries::filterNullValues)
                    .map(r -> (SquadraRecord) r)
                    .findFirst();
    }
    
    public static Optional<CalciatoreRecord> getPlayer(int playerId) {        
        return query.select()
                    .from(CALCIATORE)
                    .where(CALCIATORE.IDCALCIATORE.eq((short) playerId))
                    .stream()
                    .filter(Queries::filterNullValues)
                    .map(r -> (CalciatoreRecord) r)
                    .findFirst();
    }
    
    public static Stream<AllenatoreRecord> getAllUsers() {
        return query.select()
                    .from(ALLENATORE)
                    .fetch()
                    .stream()
                    .filter(Queries::filterNullValues)
                    .map(r -> (AllenatoreRecord) r);
    }
    
    public static Stream<CampionatoRecord> getOpenLeagues() {
        return query.select()
                    .from(CAMPIONATO)
                    .where(CAMPIONATO.DATACHIUSURA.ge(new Date(System.currentTimeMillis())))
                    .fetch()
                    .stream()
                    .filter(Queries::filterNullValues)
                    .map(r -> (CampionatoRecord) r);
    }

    public static Stream<SquadraRecord> getAllFantabidTeams() {
        return query.select()
                    .from(SQUADRA)
                    .fetch()
                    .stream()
                    .filter(Queries::filterNullValues)
                    .map(r -> (SquadraRecord) r);
    }
    
    public static Stream<String> getAllRealTeams() {
        return query.selectDistinct(CALCIATORE.SQUADRA)
                    .from(CALCIATORE)
                    .fetch()
                    .stream()
                    .filter(Queries::filterNullValues)
                    .map(r -> r.value1())
                    .sorted();
    }
    
    public static Stream<CalciatoreRecord> getAllPlayers() {
        return query.select()
                    .from(CALCIATORE)
                    .fetch()
                    .stream()
                    .filter(Queries::filterNullValues)
                    .map(r -> (CalciatoreRecord) r);
    }

    public static Stream<RegolaRecord> getAllRules() {
        return query.select()
                    .from(REGOLA)
                    .fetch()
                    .stream()
                    .filter(Queries::filterNullValues)
                    .map(r -> (RegolaRecord) r);
    }
    
    public static Stream<RegolaRecord> getRulesFromLeague(int leagueId) {
        return query.select(REGOLA.asterisk())
                    .from(REGOLE_PER_CAMPIONATO)
                    .join(REGOLA)
                    .on(REGOLE_PER_CAMPIONATO.IDREGOLA.eq(REGOLA.IDREGOLA))
                    .where(REGOLE_PER_CAMPIONATO.IDCAMPIONATO.eq(leagueId))
                    .fetch()
                    .stream()
                    .filter(Queries::filterNullValues)
                    .map(r -> r.into(REGOLA))
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
                    .filter(Queries::filterNullValues)
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
                    .filter(Queries::filterNullValues)
                    .map(r -> (PuntataRecord) r)
                    .findFirst();
    }
    
    public static Optional<Integer> getLastLeagueId() {
        return query.select(max(CAMPIONATO.IDCAMPIONATO))
                    .from(CAMPIONATO)
                    .fetch()
                    .stream()
                    .filter(Queries::filterNullValues)
                    .map(Record1::value1)
                    .findFirst();
    }
    
    public static Optional<Integer> getLastTeamId() {
        return query.select(max(SQUADRA.IDSQUADRA))
                    .from(SQUADRA)
                    .fetch()
                    .stream()
                    .filter(Queries::filterNullValues)
                    .map(Record1::value1)
                    .findFirst();
    }
    
    private static boolean filterNullValues(Object o) {
        return o != null;
    }

    // TODO: TO BE REMOVED
    public static void testQuery(Object ...args) {
//        CampionatoRecord c = new CampionatoRecord();
//        c.setNumeromassimosquadre(null);
    }
}
