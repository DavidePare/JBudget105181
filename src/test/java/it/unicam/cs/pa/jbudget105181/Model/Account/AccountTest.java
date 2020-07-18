package it.unicam.cs.pa.jbudget105181.Model.Account;

import it.unicam.cs.pa.jbudget105181.Model.IFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @BeforeEach
    void accountTester(){
        IAccount a1= IFactory.generateAccount(1,"Carta di credito","Crdito",AccountType.ASSETS,50.0);
        IAccount a2= IFactory.generateAccount(1,"Carta di debito","debito",AccountType.LIABILITIES,50.0);
    }
    @Test
    void getBalanceOfAccount() {
    }

    @Test
    void addMovement() {
    }

    @Test
    void setConto() {
    }

    @Test
    void addMovementDeserialized() {
    }
}