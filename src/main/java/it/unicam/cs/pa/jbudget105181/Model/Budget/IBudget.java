package it.unicam.cs.pa.jbudget105181.Model.Budget;

import java.util.Map;
import java.util.Set;

public interface IBudget<T extends BudgetInterface> extends BudgetInterface {
    int getID();
    void addBudgetType(T key, Double value);
    Double getValue(T key);
    Set<T> getTags();
    void remove(T key);
    Map<T, Double> getBudgetMap();
}
