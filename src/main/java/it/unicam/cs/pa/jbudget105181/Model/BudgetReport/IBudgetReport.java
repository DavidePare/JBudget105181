package it.unicam.cs.pa.jbudget105181.Model.BudgetReport;

import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;

/**
 * interfaccia che ha la responsabilita' di gestire un budget report,
 * cioe' un strumento che preso un budget e un ledger ne stipula la differenza tra il budget aspettatosi
 * dall'utente e quello reale.
 */
public interface IBudgetReport {

    /**
     * metodo responsabile di restituire per ogni tag il report
     * @return Ledger del BudgetReport.
     */
    Double check(ITag tag);
}
