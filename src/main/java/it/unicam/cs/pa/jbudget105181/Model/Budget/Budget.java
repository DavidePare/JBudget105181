package it.unicam.cs.pa.jbudget105181.Model.Budget;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Budget<T extends BudgetInterface> implements IBudget<T> {
    private int id;
    private final Map<T, Double> budgetMap;
    public Budget(){
       this.budgetMap = new HashMap<>();
    }
    public int getID(){
        return id;
    }
    /**
     * metodo per aggiungere il budget di un tag
     * @param key
     * @param value
     */
    @Override
    public void addBudgetType(T key, Double value){
        if(this.budgetMap.containsKey(key)){
            this.budgetMap.remove(key);
        }
        this.budgetMap.put(key,value);
    }

    /**
     * metodo per ottenere l'amount del
     * budget di un tag
     * @param key
     * @return
     */
    @Override
    public Double getValue(T key){
        return this.budgetMap.get(key);
    }
    /**
     * metodo per ottenere i tag
     * @return
     */
    @Override
    public Set<T> getTags() {
        return this.budgetMap.keySet();
    }
    /**
     * Metodo responsabile di eliminare un tag dal BudgetBase.
     * @param key key da rimuovere.
    */
    @Override
    public void remove(T key) {
        this.budgetMap.remove(key);
    }

    /**
     * Metodo responsabile di restituire una mappa contenente tag e valori associati.
     * @return Mappa di tag e double.
    */
    @Override
    public Map<T, Double> getBudgetMap() {
        return this.budgetMap;
    }
}
