package org.fantabid.model;

import static org.fantabid.generated.Tables.*;

import org.fantabid.Main;
import org.jooq.DSLContext;
import org.jooq.Record2;
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
                    .findFirst()
                    .filter(password::equals)
                    .isPresent();
    }
    
    public static void registerUser(String name, String surname, String user, String password) {
        query.insertInto(ALLENATORE)
             .values(user, password, name, surname)
             .execute();
    }
    
    public static Result<Record2<String, String>> getRules() {
        return query.select(REGOLA.NOME, REGOLA.DESCRIZIONE)
                    .from(REGOLA)
                    .fetch();
    }
}
