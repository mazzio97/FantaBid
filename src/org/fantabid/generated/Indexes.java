/*
 * This file is generated by jOOQ.
 */
package org.fantabid.generated;


import javax.annotation.Generated;

import org.fantabid.generated.tables.Allenatore;
import org.fantabid.generated.tables.Calciatore;
import org.fantabid.generated.tables.Campionato;
import org.fantabid.generated.tables.MembriSquadra;
import org.fantabid.generated.tables.Puntata;
import org.fantabid.generated.tables.Regola;
import org.fantabid.generated.tables.RegolePerCampionato;
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
    public static final Index IDMEMBRI_SQUADRA = Indexes0.IDMEMBRI_SQUADRA;
    public static final Index FKRIALZA_ID = Indexes0.FKRIALZA_ID;
    public static final Index IDPUNTATA = Indexes0.IDPUNTATA;
    public static final Index IDPUNTATA_1 = Indexes0.IDPUNTATA_1;
    public static final Index IDREGOLA = Indexes0.IDREGOLA;
    public static final Index IDREGOLE_PER_CAMPIONATO = Indexes0.IDREGOLE_PER_CAMPIONATO;
    public static final Index IDSQUADRA = Indexes0.IDSQUADRA;
    public static final Index IDSQUADRA_1 = Indexes0.IDSQUADRA_1;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index IDALLENATORE = Internal.createIndex("idallenatore", Allenatore.ALLENATORE, new OrderField[] { Allenatore.ALLENATORE.USERNAME }, true);
        public static Index IDCALCIATORE = Internal.createIndex("idcalciatore", Calciatore.CALCIATORE, new OrderField[] { Calciatore.CALCIATORE.IDCALCIATORE }, true);
        public static Index IDCAMPIONATO = Internal.createIndex("idcampionato", Campionato.CAMPIONATO, new OrderField[] { Campionato.CAMPIONATO.IDCAMPIONATO }, true);
        public static Index IDMEMBRI_SQUADRA = Internal.createIndex("idmembri_squadra", MembriSquadra.MEMBRI_SQUADRA, new OrderField[] { MembriSquadra.MEMBRI_SQUADRA.IDCALCIATORE, MembriSquadra.MEMBRI_SQUADRA.IDSQUADRA }, true);
        public static Index FKRIALZA_ID = Internal.createIndex("fkrialza_id", Puntata.PUNTATA, new OrderField[] { Puntata.PUNTATA.PUNTATASUCCESSIVA }, true);
        public static Index IDPUNTATA = Internal.createIndex("idpuntata", Puntata.PUNTATA, new OrderField[] { Puntata.PUNTATA.IDPUNTATA }, true);
        public static Index IDPUNTATA_1 = Internal.createIndex("idpuntata_1", Puntata.PUNTATA, new OrderField[] { Puntata.PUNTATA.VALORE, Puntata.PUNTATA.IDCALCIATORE, Puntata.PUNTATA.IDSQUADRA }, true);
        public static Index IDREGOLA = Internal.createIndex("idregola", Regola.REGOLA, new OrderField[] { Regola.REGOLA.IDREGOLA }, true);
        public static Index IDREGOLE_PER_CAMPIONATO = Internal.createIndex("idregole_per_campionato", RegolePerCampionato.REGOLE_PER_CAMPIONATO, new OrderField[] { RegolePerCampionato.REGOLE_PER_CAMPIONATO.IDCAMPIONATO, RegolePerCampionato.REGOLE_PER_CAMPIONATO.IDREGOLA }, true);
        public static Index IDSQUADRA = Internal.createIndex("idsquadra", Squadra.SQUADRA, new OrderField[] { Squadra.SQUADRA.IDSQUADRA }, true);
        public static Index IDSQUADRA_1 = Internal.createIndex("idsquadra_1", Squadra.SQUADRA, new OrderField[] { Squadra.SQUADRA.IDCAMPIONATO, Squadra.SQUADRA.USERNAME }, true);
    }
}
