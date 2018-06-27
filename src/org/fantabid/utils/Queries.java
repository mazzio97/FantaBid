package org.fantabid.utils;

import static org.fantabid.generated.Tables.*;

import org.fantabid.Main;
import org.jooq.DSLContext;
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
}
