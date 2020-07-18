package it.unicam.cs.pa.jbudget105181.Model.BudgetReport;

import it.unicam.cs.pa.jbudget105181.Model.Account.Account;
import it.unicam.cs.pa.jbudget105181.Model.Account.AccountType;
import it.unicam.cs.pa.jbudget105181.Model.Account.IAccount;
import it.unicam.cs.pa.jbudget105181.Model.Budget.Budget;
import it.unicam.cs.pa.jbudget105181.Model.Budget.IBudget;
import it.unicam.cs.pa.jbudget105181.Model.IFactory;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.ILedger;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.Ledger;
import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.Movement.Movement;
import it.unicam.cs.pa.jbudget105181.Model.Movement.MovementType;
import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;
import it.unicam.cs.pa.jbudget105181.Model.Tag.Tag;
import it.unicam.cs.pa.jbudget105181.Model.Transaction.ITransazione;
import it.unicam.cs.pa.jbudget105181.Model.Transaction.Transazione;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BudgetReportTest {
    private ILedger ledger;
    private IBudget budget;
    private IBudgetReport budgetReport;

    @BeforeEach
    void createSimpleTest(){
        this.ledger = new Ledger();
        this.budget = new Budget();
        this.budgetReport = new BudgetReport(ledger);
    }
    @Test
    void check() {
        IAccount a= IFactory.generateAccount(1,"account1","descrizione",AccountType.ASSETS,50.0);
        ITag sport = new Tag("Sport","tennis");
        ITag sport2 = new Tag("Calcio","calcetto");
        ITransazione transaction = IFactory.generateTransaction(1, LocalDate.now(), new ArrayList<ITag>() ,"descr" ,false);
        List<ITag> lTag1=new ArrayList<>();
        lTag1.add(sport);
        List<ITag> lTag2=new ArrayList<>();
        lTag2.add(sport);
        lTag2.add(sport2);
        IMovement mov1 = IFactory.generateMovement(1,"DescrA", MovementType.CREDIT,50.0,a,lTag1,transaction);
        IMovement mov2 = IFactory.generateMovement(2,"DescrB", MovementType.DEBIT,8.0,a,lTag2,transaction);
        ledger.addAccount(a);
        ledger.addTransazione(transaction);
        ledger.addMovement(mov1);
        ledger.addMovement(mov2);
        ledger.addTag(sport);
        ledger.addTag(sport2);
        ledger.addBudgetLedger(sport,100.0);
        ledger.addBudgetLedger(sport2,8.0);
        budgetReport= new BudgetReport(ledger);
        assertEquals(budgetReport.check(sport),58.0);
        assertEquals(budgetReport.check(sport2),16.0);
    }
}