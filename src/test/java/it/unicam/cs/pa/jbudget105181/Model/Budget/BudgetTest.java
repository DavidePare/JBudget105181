package it.unicam.cs.pa.jbudget105181.Model.Budget;

import it.unicam.cs.pa.jbudget105181.Model.Tag.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class BudgetTest {

    private Budget budget;
    private Tag tag1;
    private Tag tag2;

    @BeforeEach
    void createSimpleTest(){
        budget = new Budget();
        tag1 = new Tag("Sport", "Tennis");
        tag2 = new Tag("Macchina", "Revisione");
    }

    @Test
    void add() {
        assertTrue(budget.getBudgetMap().isEmpty());
        this.budget.addBudgetType(tag1,200.0);
        assertFalse(budget.getBudgetMap().isEmpty());
        assertTrue(budget.getBudgetMap().containsKey(tag1));
        assertEquals(this.budget.getValue(tag1),200.0);
    }

    @Test
    void remove() {
        this.budget.addBudgetType(tag1,20.0);
        assertFalse(budget.getBudgetMap().isEmpty());
        this.budget.remove(tag1);
        assertTrue(budget.getBudgetMap().isEmpty());
    }

    @Test
    void getBudget() {
        Map<Tag,Double> budgetMap = new HashMap<>();
        budgetMap.put(tag1,100.0);
        budget.addBudgetType(tag1,100.0);
        assertEquals(budgetMap.get(tag1),budget.getValue(this.tag1));
    }

    @Test
    void getValue() {
        budget.addBudgetType(tag1,200.0);
        budget.addBudgetType(tag2,15.0);
        assertEquals(budget.getValue(tag1),200.0);
        assertEquals(budget.getValue(tag2),15.0);
    }
}