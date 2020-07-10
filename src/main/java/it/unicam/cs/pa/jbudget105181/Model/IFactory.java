package it.unicam.cs.pa.jbudget105181.Model;

import java.util.List;

public interface IFactory {
    static ITag generateTag(String name,String description){
        return new Tag(name,description);
    }
  /*  static ITransazione generateTransaction(){
        return new Transazione();
    }*/
    static IAccount generateAccount(int id,String name,String description,AccountType type,Double amount){
        return new Account(id,name,description,type,amount);
    }
    static IMovement generateMovement(int id, String description, MovementType type, Double amount, IAccount account , List<ITag> lTags,int idTransaction){
        return new Movement(id,description,type,amount,account,lTags,idTransaction);
    }
}
