package it.unicam.cs.pa.jbudget105181.Model.Transaction;

import it.unicam.cs.pa.jbudget105181.Model.Account.AccountType;
import it.unicam.cs.pa.jbudget105181.Model.Account.IAccount;
import it.unicam.cs.pa.jbudget105181.Model.IFactory;
import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.Movement.MovementType;
import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransazioneTest {
    private IMovement movimento1;
    private IMovement movimento2;
    private IMovement movimento3;
    private ITransazione transazione1;
    private IAccount a;
    @BeforeEach
    void createTransaction(){
        this.a= IFactory.generateAccount(1,"Account","1", AccountType.ASSETS,500.0);
        List<ITag> lTag=new ArrayList<>();
        this.transazione1=IFactory.generateTransaction(1, LocalDate.now(),lTag,"ciao",true);
        this.movimento1= IFactory.generateMovement(1,"Movimento", MovementType.CREDIT,50.0,a, lTag,transazione1);
        this.movimento2= IFactory.generateMovement(1,"Movimento", MovementType.CREDIT,150.0,a, lTag,IFactory.generateTransaction(1, LocalDate.now(),lTag,"ciao",true));
        this.movimento3= IFactory.generateMovement(1,"Movimento", MovementType.DEBIT,50.0,a, lTag,IFactory.generateTransaction(1, LocalDate.now(),lTag,"ciao",true));


    }
    @Test
    void addMovement() {
        assertTrue(transazione1.getNumMov()==0);
        transazione1.addMovement(movimento1);
        transazione1.addMovement(movimento2);
        assertTrue(transazione1.getNumMov()==2);
    }

    @Test
    void movements() {
        transazione1.addMovement(movimento1);
        transazione1.addMovement(movimento2);
        List<IMovement> lMovement= transazione1.movements();
        assertFalse(lMovement.isEmpty());
        assertTrue(lMovement.contains(movimento1));
    }

    @Test
    void addTag() {
        transazione1.addTag(IFactory.generateTag("Sport","Tennis"));
        assertTrue(transazione1.tags().size()==1);
    }
    @Test
    void removeTag(){
        ITag t1= IFactory.generateTag("Sport","Tennis");
        transazione1.addTag(t1);
        transazione1.addTag(IFactory.generateTag("Sport","Calcio"));

        assertTrue(transazione1.tags().size()==2);
        transazione1.removeTag(t1);
        assertTrue(transazione1.tags().size()==1);
    }
    @Test
    void removeMovement() {
        transazione1.addMovement(movimento1);
        transazione1.addMovement(movimento2);
        transazione1.removeMovement(movimento1);
        assertTrue(transazione1.getNumMov()==1);
    }

    @Test
    void adjustTotalCost() {

        assertTrue(transazione1.getTotalAmount()==0);
        transazione1.addMovement(movimento1);
        transazione1.addMovement(movimento2);
        assertTrue(transazione1.getTotalAmount()!=0);

    }


    @Test
    void getPagata(){
        LocalDate dataAttuale= LocalDate.now();
        List<ITag> lTags= new ArrayList<>();
        ITransazione t1= IFactory.generateTransaction(1, dataAttuale,lTags,"Account1",!dataAttuale.isAfter(LocalDate.now()));
        LocalDate dataFutura= LocalDate.of(2021,10,6);
        ITransazione t2= IFactory.generateTransaction(1, dataAttuale,lTags,"Account1",!dataFutura.isAfter(LocalDate.now()));
        assertTrue(t1.getPagata());
        assertFalse(t2.getPagata());
    }
}