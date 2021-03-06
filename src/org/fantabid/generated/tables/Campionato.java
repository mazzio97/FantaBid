/*
 * This file is generated by jOOQ.
 */
package org.fantabid.generated.tables;


import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.fantabid.generated.Indexes;
import org.fantabid.generated.Keys;
import org.fantabid.generated.Public;
import org.fantabid.generated.tables.records.CampionatoRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Campionato extends TableImpl<CampionatoRecord> {

    private static final long serialVersionUID = 1587423849;

    /**
     * The reference instance of <code>public.campionato</code>
     */
    public static final Campionato CAMPIONATO = new Campionato();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CampionatoRecord> getRecordType() {
        return CampionatoRecord.class;
    }

    /**
     * The column <code>public.campionato.idcampionato</code>.
     */
    public final TableField<CampionatoRecord, Integer> IDCAMPIONATO = createField("idcampionato", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.campionato.nomecampionato</code>.
     */
    public final TableField<CampionatoRecord, String> NOMECAMPIONATO = createField("nomecampionato", org.jooq.impl.SQLDataType.VARCHAR(40).nullable(false), this, "");

    /**
     * The column <code>public.campionato.descrizione</code>.
     */
    public final TableField<CampionatoRecord, String> DESCRIZIONE = createField("descrizione", org.jooq.impl.SQLDataType.VARCHAR(1000).nullable(false), this, "");

    /**
     * The column <code>public.campionato.budgetpersquadra</code>.
     */
    public final TableField<CampionatoRecord, Short> BUDGETPERSQUADRA = createField("budgetpersquadra", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.campionato.dataapertura</code>.
     */
    public final TableField<CampionatoRecord, Date> DATAAPERTURA = createField("dataapertura", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "");

    /**
     * The column <code>public.campionato.datachiusura</code>.
     */
    public final TableField<CampionatoRecord, Date> DATACHIUSURA = createField("datachiusura", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "");

    /**
     * The column <code>public.campionato.astarialzo</code>.
     */
    public final TableField<CampionatoRecord, Boolean> ASTARIALZO = createField("astarialzo", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.campionato.numeromassimosquadre</code>.
     */
    public final TableField<CampionatoRecord, Byte> NUMEROMASSIMOSQUADRE = createField("numeromassimosquadre", org.jooq.impl.SQLDataType.TINYINT, this, "");

    /**
     * Create a <code>public.campionato</code> table reference
     */
    public Campionato() {
        this(DSL.name("campionato"), null);
    }

    /**
     * Create an aliased <code>public.campionato</code> table reference
     */
    public Campionato(String alias) {
        this(DSL.name(alias), CAMPIONATO);
    }

    /**
     * Create an aliased <code>public.campionato</code> table reference
     */
    public Campionato(Name alias) {
        this(alias, CAMPIONATO);
    }

    private Campionato(Name alias, Table<CampionatoRecord> aliased) {
        this(alias, aliased, null);
    }

    private Campionato(Name alias, Table<CampionatoRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Campionato(Table<O> child, ForeignKey<O, CampionatoRecord> key) {
        super(child, key, CAMPIONATO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.IDCAMPIONATO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CampionatoRecord> getPrimaryKey() {
        return Keys.IDCAMPIONATO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CampionatoRecord>> getKeys() {
        return Arrays.<UniqueKey<CampionatoRecord>>asList(Keys.IDCAMPIONATO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Campionato as(String alias) {
        return new Campionato(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Campionato as(Name alias) {
        return new Campionato(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Campionato rename(String name) {
        return new Campionato(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Campionato rename(Name name) {
        return new Campionato(name, null);
    }
}
