package it.unicam.cs.pa.jbudget105181.Model.Budget;

import java.util.Map;
import java.util.Set;

/**
 * interfaccia che ha la responsabilita' di gestire una serie di budget,
 * ognuno associato ad tag.
 */
public interface IBudget<T extends BudgetInterface> extends BudgetInterface {

    /**
     * metodo per ottenere l'ID
     * @return
     */
    int getID(); // TODO cosa ci fai?

    /**
     * metodo per aggiungere il budget di un tag
     * @param key
     * @param value
     */
    void addBudgetType(T key, Double value);

    /**
     * metodo per ottenere l'amount del
     * budget di un tag
     * @param key
     * @return
     */
    Double getValue(T key);

    /**
     * metodo per ottenere i tag
     * @return
     */
    Set<T> getTags();

    /**
     * metodo per rimuovere il budget di un tag
     * @param key
     */
    void remove(T key);

    /**
     * metodo per ottenere tutti i budget
     * @return
     */
    Map<T, Double> getBudgetMap();
}
