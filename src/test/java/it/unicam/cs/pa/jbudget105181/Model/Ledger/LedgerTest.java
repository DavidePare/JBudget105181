package it.unicam.cs.pa.jbudget105181.Model.Ledger;

import it.unicam.cs.pa.jbudget105181.Model.Account.AccountType;
import it.unicam.cs.pa.jbudget105181.Model.Account.IAccount;
import it.unicam.cs.pa.jbudget105181.Model.IFactory;
import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.Movement.MovementType;
import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;
import it.unicam.cs.pa.jbudget105181.Model.Transaction.ITransazione;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LedgerTest {
    private ILedger ledger;
    private ITransazione t1;

    private IAccount account1;
    private IMovement Movement1;
    private IMovement Movement2;
    private IMovement Movement3;
    private IMovement Movement4;
    private ITag tag1;
    private ITag tag2;
    @BeforeEach
    void createSimpleData(){
        this.ledger= new Ledger();
        tag1= IFactory.generateTag("Sport","Tennis");
        tag2= IFactory.generateTag("Calcio","calcetto a 5");
        List<ITag> lTags= new ArrayList<>();
        lTags.add(tag1);
        account1 = IFactory.generateAccount(1,"Davide","AccountTest", AccountType.ASSETS,25.0);

        ledger.addAccount(account1);
        LocalDate dataAttuale= LocalDate.now();
        t1= IFactory.generateTransaction(1, dataAttuale,lTags,"Account1",!dataAttuale.isAfter(LocalDate.now()));
        Movement1 = IFactory.generateMovement(1,"Movimento1", MovementType.CREDIT,140.0,account1,lTags,t1);

        Movement2 = IFactory.generateMovement(2,"Movimento2",MovementType.CREDIT,60.0,account1,lTags,t1);

        Movement3 = IFactory.generateMovement(3,"Movimento3",MovementType.DEBIT,5.0,account1,lTags,t1);
        Movement4 = IFactory.generateMovement(4,"Movimento4",MovementType.DEBIT,15.0,account1,lTags,t1);
        t1.addMovement(Movement1);
        t1.addMovement(Movement2);
        t1.addMovement(Movement3);
        ledger.addTag(tag1);
        ledger.addTag(tag2);
        ledger.addTransazione(t1);
    }
    @Test
    void addTransazione() {
        assertFalse(ledger.getAllTransactions().isEmpty());
        LocalDate dataAttuale= LocalDate.now();
        List<ITag> lEmpty= new ArrayList<>();
        ITransazione t2= IFactory.generateTransaction(1, dataAttuale,lEmpty,"Transaction2",!dataAttuale.isAfter(LocalDate.now()));
        ledger.addTransazione(t2);
        assertEquals(ledger.getAllTransactions().size(),2);
    }

    @Test
    void getAllTransactions() {
        assertFalse(this.ledger.getAllTransactions().isEmpty());
        this.ledger.getAllTransactions().remove(t1);
        assertTrue(this.ledger.getAllTransactions().isEmpty());
    }

    @Test
    void addTag() {
        ledger.addTag(IFactory.generateTag("TestAdd","AddTag"));
        ITag tag=ledger.getTags().stream()
                .filter(t-> t.getNome().compareTo("TestAdd")==0 && t.getDescription().compareTo("AddTag")==0)
                .findAny()
                .get();
        assertTrue(tag.getNome().compareTo("TestAdd") ==0);
    }
    @Test
    void getTags() {
        assertEquals(ledger.getTags().size(),2);
    }

    @Test
    void getAccounts() {
        assertEquals(ledger.getAccounts().size(),1);
    }

    @Test
    void addAccount() {
        IAccount account2= IFactory.generateAccount(2,"Luca","AccountTest", AccountType.LIABILITIES,125.0);
        ledger.addAccount(account2);
        assertEquals(ledger.getAccounts().size(),2);
    }

    @Test
    void removeTag() {
        ledger.removeTag(p-> p.getNome().compareTo("Sport")==0 && p.getDescription().compareTo("Tennis")==0);
        assertEquals(ledger.getTags().size(),1);
    }

    @Test
    void removeMovement() {
        ledger.removeMovement(Movement1);
        assertEquals(t1.getNumMov(),2);
    }

    @Test
    void getAccountForID() {
        IAccount a=ledger.getAccountForID(1);
        assertTrue(a.equals(account1));
    }

    @Test
    void modifyAccount() {
        ledger.addMovement(Movement1);
        ledger.addMovement(Movement2);
        ledger.addMovement(Movement3);
        ledger.modifyAccount(1,"ciao","modificato",AccountType.ASSETS,0.0);
        assertTrue(ledger.getAccountForID(1).getDescriptionAccount().compareTo("modificato")==0);
        assertEquals(ledger.getAccountForID(1).getBalanceOfAccount(),195.0);
    }

    @Test
    void addMovement() {
        ledger.addMovement(Movement4);
        assertEquals(ledger.getTransaction(p-> p.getID()== Movement4.getIDTransazione()).size(),1);
    }



    @Test
    void getATag() {
        ITag tag= ledger.getATag("Sport","Tennis");
        assertTrue(tag.equals(tag1));
    }


    @RepeatedTest(5)
    void generateID(){
        int idTransazione=ledger.generateID(ledger.getAllTransactions());
        List<ITag> lTags=new ArrayList<>();
        assertTrue(ledger.getTransaction(p-> p.getID() == idTransazione).isEmpty());
        ledger.addTransazione(IFactory.generateTransaction(idTransazione, LocalDate.now(),lTags,"Account1",false));

        assertFalse(ledger.getTransaction(p-> p.getID() == idTransazione).isEmpty());
    }
}