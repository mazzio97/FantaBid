/*
 * This file is generated by jOOQ.
 */
package org.fantabid.generated;


import javax.annotation.Generated;

import org.fantabid.generated.tables.Allenatore;
import org.fantabid.generated.tables.Calciatore;
import org.fantabid.generated.tables.Campionato;
import org.fantabid.generated.tables.Puntata;
import org.fantabid.generated.tables.Regola;
import org.fantabid.generated.tables.Squadra;
import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>public</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index IDALLENATORE = Indexes0.IDALLENATORE;
    public static final Index IDCALCIATORE = Indexes0.IDCALCIATORE;
    public static final Index IDCAMPIONATO = Indexes0.IDCAMPIONATO;
    public static final Index FKRIALZA_ID = Indexes0.FKRIALZA_ID;
    public static final Index IDPUNTATA = Indexes0.IDPUNTATA;
    public static final Index IDREGOLA = Indexes0.IDREGOLA;
    public static final Index IDSQUADRA = Indexes0.IDSQUADRA;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index IDALLENATORE = Internal.createIndex("idallenatore", Allenatore.ALLENATORE, new OrderField[] { Allenatore.ALLENATORE.USERNAME }, true);
        public static Index IDCALCIATORE = Internal.createIndex("idcalciatore", Calciatore.CALCIATORE, new OrderField[] { Calciatore.CALCIATORE.IDCALCIATORE }, true);
        public static Index IDCAMPIONATO = Internal.createIndex("idcampionato", Campionato.CAMPIONATO, new OrderField[] { Campionato.CAMPIONATO.IDCAMPIONATO }, true);
        public static Index FKRIALZA_ID = Internal.createIndex("fkrialza_id", Puntata.PUNTATA, new OrderField[] { Puntata.PUNTATA.SUCCESSIVA_IDCALCIATORE, Puntata.PUNTATA.SUCCESSIVA_USERNAME, Puntata.PUNTATA.SUCCESSIVA_IDCAMPIONATO, Puntata.PUNTATA.SUCCESSIVA_VALORE }, true);
        public static Index IDPUNTATA = Internal.createIndex("idpuntata", Puntata.PUNTATA, new OrderField[] { Puntata.PUNTATA.IDCALCIATORE, Puntata.PUNTATA.USERNAME, Puntata.PUNTATA.IDCAMPIONATO, Puntata.PUNTATA.VALORE }, true);
        public static Index IDREGOLA = Internal.createIndex("idregola", Regola.REGOLA, new OrderField[] { Regola.REGOLA.IDREGOLA }, true);
        public static Index IDSQUADRA = Internal.createIndex("idsquadra", Squadra.SQUADRA, new OrderField[] { Squadra.SQUADRA.USERNAME, Squadra.SQUADRA.IDCAMPIONATO }, true);
    }
}
