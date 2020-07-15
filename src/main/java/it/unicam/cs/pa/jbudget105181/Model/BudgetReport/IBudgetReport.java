package it.unicam.cs.pa.jbudget105181.Model.BudgetReport;

import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;

public interface IBudgetReport {
    /**
     * Metodo responsabile di restituire per ogni tag comuni a ledger e budget la differenza tra
     * la differenza tra il saldo dei movimenti e il valore associato ad ogni tag nel budget.
     * @return Ledger del BudgetReport.
     */
    Double check(ITag tag);
}
