/*
 * This file is generated by jOOQ.
 */
package org.fantabid.generated.tables.records;


import javax.annotation.Generated;

import org.fantabid.generated.tables.RegolePerCampionato;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


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
public class RegolePerCampionatoRecord extends UpdatableRecordImpl<RegolePerCampionatoRecord> implements Record2<Short, Integer> {

    private static final long serialVersionUID = 1362668820;

    /**
     * Setter for <code>public.regole_per_campionato.idregola</code>.
     */
    public void setIdregola(Short value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.regole_per_campionato.idregola</code>.
     */
    public Short getIdregola() {
        return (Short) get(0);
    }

    /**
     * Setter for <code>public.regole_per_campionato.idcampionato</code>.
     */
    public void setIdcampionato(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.regole_per_campionato.idcampionato</code>.
     */
    public Integer getIdcampionato() {
        return (Integer) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record2<Integer, Short> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Short, Integer> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Short, Integer> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field1() {
        return RegolePerCampionato.REGOLE_PER_CAMPIONATO.IDREGOLA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return RegolePerCampionato.REGOLE_PER_CAMPIONATO.IDCAMPIONATO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short component1() {
        return getIdregola();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getIdcampionato();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value1() {
        return getIdregola();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getIdcampionato();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegolePerCampionatoRecord value1(Short value) {
        setIdregola(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegolePerCampionatoRecord value2(Integer value) {
        setIdcampionato(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegolePerCampionatoRecord values(Short value1, Integer value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached RegolePerCampionatoRecord
     */
    public RegolePerCampionatoRecord() {
        super(RegolePerCampionato.REGOLE_PER_CAMPIONATO);
    }

    /**
     * Create a detached, initialised RegolePerCampionatoRecord
     */
    public RegolePerCampionatoRecord(Short idregola, Integer idcampionato) {
        super(RegolePerCampionato.REGOLE_PER_CAMPIONATO);

        set(0, idregola);
        set(1, idcampionato);
    }
}