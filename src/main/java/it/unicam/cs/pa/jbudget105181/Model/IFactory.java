package it.unicam.cs.pa.jbudget105181.Model;

import it.unicam.cs.pa.jbudget105181.Model.Account.Account;
import it.unicam.cs.pa.jbudget105181.Model.Account.AccountType;
import it.unicam.cs.pa.jbudget105181.Model.Account.IAccount;
import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.Movement.Movement;
import it.unicam.cs.pa.jbudget105181.Model.Movement.MovementType;
import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;
import it.unicam.cs.pa.jbudget105181.Model.Tag.Tag;
import it.unicam.cs.pa.jbudget105181.Model.Transaction.ITransazione;
import it.unicam.cs.pa.jbudget105181.Model.Transaction.Transazione;

import java.time.LocalDate;
import java.util.List;

public interface IFactory {
    static ITag generateTag(String name, String description){
        return new Tag(name,description);
    }

    static IAccount generateAccount(int id, String name, String description, AccountType type, Double amount){
        return new Account(id,name,description,type,amount);
    }
    static IMovement generateMovement(int id, String description, MovementType type, Double amount, IAccount account , List<ITag> lTags, ITransazione transaction){
        return new Movement(id,description,type,amount,account,lTags,transaction);
    }
    static ITransazione generateTransaction(int id, LocalDate data, List<ITag> tag, String description, boolean pagata){
        return new Transazione(id,data,tag,description,false);
    }
}
