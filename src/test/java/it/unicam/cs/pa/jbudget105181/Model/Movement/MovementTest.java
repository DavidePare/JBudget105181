package it.unicam.cs.pa.jbudget105181.Model.Movement;

import it.unicam.cs.pa.jbudget105181.Model.Account.AccountType;
import it.unicam.cs.pa.jbudget105181.Model.Account.IAccount;
import it.unicam.cs.pa.jbudget105181.Model.IFactory;
import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;
import it.unicam.cs.pa.jbudget105181.Model.Transaction.ITransazione;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovementTest {
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
        tag1= IFactory.generateTag("Sport","Tennis");
        tag2= IFactory.generateTag("Calcio","calcetto a 5");
        List<ITag> lTags= new ArrayList<>();
        lTags.add(tag1);
        account1 = IFactory.generateAccount(1,"Davide","AccountTest", AccountType.ASSETS,25.0);
        LocalDate dataAttuale= LocalDate.now();
        t1= IFactory.generateTransaction(1, dataAttuale,lTags,"Account1",!dataAttuale.isAfter(LocalDate.now()));
        Movement1 = IFactory.generateMovement(1,"Movimento1",MovementType.CREDIT,140.0,account1,lTags,t1);

        Movement2 = IFactory.generateMovement(2,"Movimento2",MovementType.CREDIT,60.0,account1,lTags,t1);

        Movement3 = IFactory.generateMovement(3,"Movimento3",MovementType.DEBIT,5.0,account1,lTags,t1);
        Movement4 = IFactory.generateMovement(4,"Movimento4",MovementType.DEBIT,15.0,account1,lTags,t1);
        t1.addMovement(Movement1);
        t1.addMovement(Movement2);
    }
    @Test
    void getAmount() {
        assertEquals(Movement1.getAmount(),140.0);
    }

    @Test
    void tags() {
        assertTrue(Movement1.tags().size()==1);
    }

    @Test
    void addTag() {
        assertTrue(Movement1.tags().size()==1);
        Movement1.addTag(tag2);

        assertTrue(Movement1.tags().size()==2);
    }

    @Test
    void getTransaction() {
        assertTrue(Movement1.getTransaction().equals(t1));
    }

    @Test
    void removeTag() {
        assertTrue(Movement1.tags().size()==1);
        List<ITag> tagRemove=new ArrayList<>();
        tagRemove.add(tag1);
        Movement1.removeTag(tagRemove);
        assertTrue(Movement1.tags().isEmpty());
    }
}