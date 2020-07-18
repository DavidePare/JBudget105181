package it.unicam.cs.pa.jbudget105181.Model;

import it.unicam.cs.pa.jbudget105181.Model.Budget.BudgetInterface;

/**
 * interfaccia che ha la responsabilita' di garantire
 * metodi per accedere e modificare alcuni campi specifici.
 */
public interface IUtility extends BudgetInterface {

    /**
     * metodo per ritornare l'ID dell'oggetto
     * @return id associato
     */
    int getID();
}