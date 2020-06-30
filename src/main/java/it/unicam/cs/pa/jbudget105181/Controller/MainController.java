package it.unicam.cs.pa.jbudget105181.Controller;

import it.unicam.cs.pa.jbudget105181.Model.ITag;
import it.unicam.cs.pa.jbudget105181.Model.Ledger;
import it.unicam.cs.pa.jbudget105181.Model.Tag;

import java.util.List;

public class MainController {

    private Ledger ledger;

    public MainController() {
        this.ledger = new Ledger();
    }

    public void addTag(ITag tag){
        ledger.addTag(tag);
    }

    public List<ITag> getTags(){
        return ledger.getTags();
    }

    public void removeTag(ITag tag){
        ledger.removeTag(t->t.getNome()==tag.getNome());
    }
}
