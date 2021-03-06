/*
 * This file is generated by jOOQ.
 */
package org.fantabid.generated.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.fantabid.generated.Indexes;
import org.fantabid.generated.Keys;
import org.fantabid.generated.Public;
import org.fantabid.generated.tables.records.RegolePerCampionatoRecord;
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
public class RegolePerCampionato extends TableImpl<RegolePerCampionatoRecord> {

    private static final long serialVersionUID = -1549224222;

    /**
     * The reference instance of <code>public.regole_per_campionato</code>
     */
    public static final RegolePerCampionato REGOLE_PER_CAMPIONATO = new RegolePerCampionato();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RegolePerCampionatoRecord> getRecordType() {
        return RegolePerCampionatoRecord.class;
    }

    /**
     * The column <code>public.regole_per_campionato.idregola</code>.
     */
    public final TableField<RegolePerCampionatoRecord, Short> IDREGOLA = createField("idregola", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.regole_per_campionato.idcampionato</code>.
     */
    public final TableField<RegolePerCampionatoRecord, Integer> IDCAMPIONATO = createField("idcampionato", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>public.regole_per_campionato</code> table reference
     */
    public RegolePerCampionato() {
        this(DSL.name("regole_per_campionato"), null);
    }

    /**
     * Create an aliased <code>public.regole_per_campionato</code> table reference
     */
    public RegolePerCampionato(String alias) {
        this(DSL.name(alias), REGOLE_PER_CAMPIONATO);
    }

    /**
     * Create an aliased <code>public.regole_per_campionato</code> table reference
     */
    public RegolePerCampionato(Name alias) {
        this(alias, REGOLE_PER_CAMPIONATO);
    }

    private RegolePerCampionato(Name alias, Table<RegolePerCampionatoRecord> aliased) {
        this(alias, aliased, null);
    }

    private RegolePerCampionato(Name alias, Table<RegolePerCampionatoRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> RegolePerCampionato(Table<O> child, ForeignKey<O, RegolePerCampionatoRecord> key) {
        super(child, key, REGOLE_PER_CAMPIONATO);
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
        return Arrays.<Index>asList(Indexes.IDREGOLE_PER_CAMPIONATO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<RegolePerCampionatoRecord> getPrimaryKey() {
        return Keys.IDREGOLE_PER_CAMPIONATO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<RegolePerCampionatoRecord>> getKeys() {
        return Arrays.<UniqueKey<RegolePerCampionatoRecord>>asList(Keys.IDREGOLE_PER_CAMPIONATO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<RegolePerCampionatoRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<RegolePerCampionatoRecord, ?>>asList(Keys.REGOLE_PER_CAMPIONATO__FKR_1, Keys.REGOLE_PER_CAMPIONATO__FKR);
    }

    public Regola regola() {
        return new Regola(this, Keys.REGOLE_PER_CAMPIONATO__FKR_1);
    }

    public Campionato campionato() {
        return new Campionato(this, Keys.REGOLE_PER_CAMPIONATO__FKR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegolePerCampionato as(String alias) {
        return new RegolePerCampionato(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegolePerCampionato as(Name alias) {
        return new RegolePerCampionato(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public RegolePerCampionato rename(String name) {
        return new RegolePerCampionato(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public RegolePerCampionato rename(Name name) {
        return new RegolePerCampionato(name, null);
    }
}
