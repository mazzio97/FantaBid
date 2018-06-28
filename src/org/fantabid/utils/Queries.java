package org.fantabid.utils;

import static org.fantabid.generated.Tables.*;

import java.util.stream.Stream;

import org.fantabid.Main;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
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
                    .fetch()
                    .stream()
                    .map(r -> r.field1() + " (" + r.field2() + "), closing: " + r.field3());
    }

    // TODO: TO BE REMOVED
    public static Result<?> testQuery(Object ...args) {
        return query.select()
                    .from(CAMPIONATO)
                    .fetch();
    }
}
