package it.unicam.cs.pa.jbudget105181.Model.Tag;

import it.unicam.cs.pa.jbudget105181.Model.Account.AccountType;
import it.unicam.cs.pa.jbudget105181.Model.Account.IAccount;
import it.unicam.cs.pa.jbudget105181.Model.IFactory;
import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.Movement.MovementType;
import it.unicam.cs.pa.jbudget105181.Model.Transaction.Transazione;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {
    private IMovement mov1,mov2,mov3;
    private ITag t1;
    private ITag t2;
    private IAccount a;
    @BeforeEach
    void createSimpleData(){
        this.t1= IFactory.generateTag("Sport","Tennis");
        List<ITag> lTag=new ArrayList<>();
        lTag.add(t1);
        this.a= IFactory.generateAccount(1,"Account","1", AccountType.ASSETS,500.0);
        this.mov1= IFactory.generateMovement(1,"Movimento", MovementType.CREDIT,50.0,a, lTag,IFactory.generateTransaction(1, LocalDate.now(),lTag,"ciao",true));
        this.mov2= IFactory.generateMovement(1,"Movimento", MovementType.CREDIT,150.0,a, lTag,IFactory.generateTransaction(1, LocalDate.now(),lTag,"ciao",true));
        this.mov3= IFactory.generateMovement(1,"Movimento", MovementType.DEBIT,50.0,a, lTag,IFactory.generateTransaction(1, LocalDate.now(),lTag,"ciao",true));

    }
    @Test
    void addMovement() {
        assertTrue(t1.getAmount()==0);
        assertFalse(t1.getAmount()!=0);
        t1.addMovement(mov1);
        assertTrue(t1.getAmount() ==50.0);
    }

    @Test
    void deleteMovement() {
        t1.addMovement(mov1);
        t1.addMovement(mov2);
        t1.addMovement(mov3);
        assertTrue(t1.getAmount()==150);
        t1.deleteMovement(mov1);
        assertTrue(t1.getAmount()==100);
    }

    @Test
    void getAmount() {
        assertTrue(t1.getAmount()==0.0);
        assertFalse(t1.getAmount()!=0);
        t1.addMovement(mov1);
        assertTrue(t1.getAmount() ==50.0);
        t1.addMovement(mov2);
        assertFalse(t1.getAmount() ==50.0);
        assertTrue(t1.getAmount() ==200.0);
        t1.addMovement(mov3);
        assertTrue(t1.getAmount()==150.0);
    }
}