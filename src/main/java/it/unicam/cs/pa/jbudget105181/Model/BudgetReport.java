package it.unicam.cs.pa.jbudget105181.Model;

import java.util.HashMap;
import java.util.Map;

public class BudgetReport implements IBudgetReport{
    /**
     * Ledger del BudgetReportBase.
     */
    private final ILedger ledger;
    /**
     * Costruttore del BudgetReportBase.
     * @param ledger Ledger del BudgetReportBase.
     */
    public BudgetReport(ILedger ledger) {
        this.ledger=ledger;
    }


    /**
     * Metodo responsabile di restituire per ogni tag comuni a ledger e budget la differenza tra
     * la differenza tra il saldo dei movimenti e il valore associato ad ogni tag nel budget.
     * @return Ledger del BudgetReportBase.
     */
    @Override
    public Double check(ITag tag) {
        return ledger.getBudget().getValue(tag)-tag.getAmount();
    }
}
