package org.fantabid.model;

import static org.fantabid.generated.Tables.*;
import static org.jooq.impl.DSL.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
                                                  Date closure, boolean bid, Byte maxTeams) {
        CampionatoRecord league = new CampionatoRecord();
        league.setIdcampionato(getLastLeagueId().orElse(-1) + 1);
        league.setNomecampionato(leagueName);
        league.setDescrizione(description);
        league.setBudgetpersquadra((short) budget);
        league.setDataapertura(opening);
        league.setDatachiusura(closure);
        league.setAstarialzo(bid);
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
    
    public static void registerBet(int teamId, int playerId, int value) {
        Optional<PuntataRecord> oldBet = Queries.getLastBet(Queries.getTeam(teamId).get().getIdcampionato(), playerId);
        int newBetId = getLastBetId().orElse(0) + 1;
        query.insertInto(PUNTATA, PUNTATA.IDPUNTATA, PUNTATA.IDSQUADRA, PUNTATA.IDCALCIATORE, PUNTATA.VALORE)
             .values(newBetId, teamId, (short) playerId, (short) value)
             .execute();
        oldBet.ifPresent(p -> {
            query.update(PUNTATA)
                 .set(PUNTATA.PUNTATASUCCESSIVA, newBetId)
                 .where(PUNTATA.IDPUNTATA.eq(p.getIdpuntata()))
                 .execute();
            removePlayerFromTeam(p.getIdsquadra(), (short) playerId);
            updateBudgetLeft(p.getIdsquadra(), p.getValore());
        });
        insertPlayerIntoTeam(teamId, (short) playerId);
        updateBudgetLeft(teamId, -value);
    }
    
    public static void updateBudgetLeft(int teamId, int amount) {
        query.update(SQUADRA)
             .set(SQUADRA.CREDITORESIDUO, (short) (getTeamBudget(teamId) + amount))
             .where(SQUADRA.IDSQUADRA.eq(teamId))
             .execute();
    }
    
    public static int getTeamBudget(int teamId) {
        return getTeam(teamId).map(s -> s.getCreditoresiduo())
                              .orElseThrow(() -> new IllegalArgumentException());
    }

    public static void linkRuleToLeague(int ruleId, int leagueId) {
        query.insertInto(REGOLE_PER_CAMPIONATO)
             .values(ruleId, leagueId)
             .execute();
    }
    
    public static void insertPlayerIntoTeam(int teamId, int playerId) {
        query.insertInto(MEMBRI_SQUADRA)
             .values(teamId, playerId)
             .execute();
        
    }
    
    public static void removePlayerFromTeam(int teamId, int playerId) {
        query.delete(MEMBRI_SQUADRA)
             .where(MEMBRI_SQUADRA.IDSQUADRA.eq(teamId))
             .and(MEMBRI_SQUADRA.IDCALCIATORE.eq((short) playerId))
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
                .map(Record1::value1)
                .filter(password::equals)
                .findFirst()
                .isPresent();
    }
    
    public static Optional<AllenatoreRecord> getUser(String username) {
        return query.select()
                    .from(ALLENATORE)
                    .where(ALLENATORE.USERNAME.eq(username))
                    .stream()
                    .map(r -> r.into(ALLENATORE))
                    .findFirst();
    }
    
    public static Optional<CampionatoRecord> getLeague(int leagueId) {
        return query.select()
                    .from(CAMPIONATO)
                    .where(CAMPIONATO.IDCAMPIONATO.eq(leagueId))
                    .stream()
                    .map(r -> r.into(CAMPIONATO))
                    .findFirst();
    }
    
    public static Optional<SquadraRecord> getTeam(int teamId) {
        return query.select()
                    .from(SQUADRA)
                    .where(SQUADRA.IDSQUADRA.eq(teamId))
                    .stream()
                    .map(r -> r.into(SQUADRA))
                    .findFirst();
    }
    
    public static Optional<CalciatoreRecord> getPlayer(int playerId) {        
        return query.select()
                    .from(CALCIATORE)
                    .where(CALCIATORE.IDCALCIATORE.eq((short) playerId))
                    .stream()
                    .map(r -> r.into(CALCIATORE))
                    .findFirst();
    }
    
    public static Stream<AllenatoreRecord> getAllUsers() {
        return query.select()
                    .from(ALLENATORE)
                    .fetch()
                    .stream()
                    .map(r -> r.into(ALLENATORE));
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
    
    public static Stream<CampionatoRecord> getNotRegisteredLeagues(String user) {
        List<CampionatoRecord> leagues = query.select()
                                              .from(CAMPIONATO)
                                              .where(CAMPIONATO.DATACHIUSURA.ge(new Date(System.currentTimeMillis())))
                                              .fetch()
                                              .stream()
                                              .map(r -> r.into(CAMPIONATO))
                                              .collect(Collectors.toList());
        leagues.removeAll(Queries.getTeamsFromUser(user).map(Pair::getSecond).collect(Collectors.toSet()));
        return leagues.stream();
    }

    public static Stream<RegolaRecord> getRulesFromLeague(int leagueId) {
        return query.select(REGOLA.asterisk())
                    .from(REGOLE_PER_CAMPIONATO)
                    .join(REGOLA)
                    .on(REGOLE_PER_CAMPIONATO.IDREGOLA.eq(REGOLA.IDREGOLA))
                    .where(REGOLE_PER_CAMPIONATO.IDCAMPIONATO.eq(leagueId))
                    .fetch()
                    .stream()
                    .map(r -> r.into(REGOLA));
    }

    
    public static Stream<CalciatoreRecord> getAllPlayersOfTeam(int teamId) {
        return query.select(CALCIATORE.asterisk())
                    .from(MEMBRI_SQUADRA)
                    .join(CALCIATORE)
                    .on(MEMBRI_SQUADRA.IDCALCIATORE.eq(CALCIATORE.IDCALCIATORE))
                    .where(MEMBRI_SQUADRA.IDSQUADRA.eq(teamId))
                    .fetch()
                    .stream()
                    .map(r -> r.into(CALCIATORE));
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
                    .map(r -> r.into(PUNTATA))
                    .findFirst();
    }
    
    public static Stream<SquadraRecord> getAllFantabidTeams() {
        return query.select()
                    .from(SQUADRA)
                    .fetch()
                    .stream()
                    .map(r -> r.into(SQUADRA));
    }
    
    public static Stream<String> getAllRealTeams() {
        return query.selectDistinct(CALCIATORE.SQUADRA)
                    .from(CALCIATORE)
                    .fetch()
                    .stream()
                    .map(Record1::value1)
                    .sorted();
    }
    
    public static Stream<CalciatoreRecord> getAllPlayers() {
        return query.select()
                    .from(CALCIATORE)
                    .fetch()
                    .stream()
                    .map(r -> r.into(CALCIATORE));
    }

    public static Stream<RegolaRecord> getAllRules() {
        return query.select()
                    .from(REGOLA)
                    .fetch()
                    .stream()
                    .map(r -> r.into(REGOLA));
    }
    
    public static Optional<Integer> getLastLeagueId() {
        return query.select(max(CAMPIONATO.IDCAMPIONATO))
                    .from(CAMPIONATO)
                    .fetch()
                    .stream()
                    .map(Record1::value1)
                    .filter(Queries::filterNullValues)
                    .findFirst();
    }
    
    public static Optional<Integer> getLastTeamId() {
        return query.select(max(SQUADRA.IDSQUADRA))
                    .from(SQUADRA)
                    .fetch()
                    .stream()
                    .map(Record1::value1)
                    .filter(Queries::filterNullValues)
                    .findFirst();
    }
    
    public static Optional<Integer> getLastBetId() {
        return query.select(max(PUNTATA.IDPUNTATA))
                    .from(PUNTATA)
                    .fetch()
                    .stream()
                    .map(Record1::value1)
                    .filter(Queries::filterNullValues)
                    .findFirst();
    }
    
    private static boolean filterNullValues(Object o) {
        return o != null;
    }

    // TODO: TO BE REMOVED
    public static void testQuery(Object ...args) {
    }
}
