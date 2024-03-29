package it.unicam.cs.pa.jbudget105181.Model.Store;

import com.google.gson.*;
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
import javafx.scene.input.InputMethodTextRun;
import netscape.javascript.JSObject;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class AdapterLedger implements JsonSerializer<ILedger>, JsonDeserializer<ILedger>{
    /**
     * ledger a cui avverrà la deserializzazione del file json
     */
    private ILedger ledger;

    /**
     * Deserializzatore del file json richiamerà tutte le funzioni di deserializzazione per la corretta lettura del file
     * json richiamando i successivi deserializzatori
     * @param json
     * @param typeOfT
     * @param context
     * @return ritorno del ledger
     * @throws JsonParseException
     */
    @Override
    public ILedger deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        this.ledger=new Ledger();
        ledger.addTags(tagsDeserialize(json.getAsJsonObject().get("Tags"), context));
        ledger.setBudget(deserializeBudget(json.getAsJsonObject().get("Budget"),context));
        ledger.addAccounts(accountsDeserialize(json.getAsJsonObject().get("Accounts"),context));
        ledger.addTransactions(transactionsDeserialize(json.getAsJsonObject().get("Transactions"),context));
        return ledger;
    }

    /**
     * Serializzatore del ledger per creare il file gson da salvare sul file
     * @param src
     * @param typeOfSrc
     * @param context
     * @return ritorna il jsonObject completo
     */
    @Override
    public JsonElement serialize(ILedger src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jo = new JsonObject();
        jo.add("Tags",tagsSerializer(src.getTags(), context));
        jo.add("Accounts", accountsSerializer(src.getAccounts(),context));
        jo.add("Transactions", transactionsSerializer(src.getAllTransactions(),context));
        jo.add("Budget",serializeBudget(src.getBudget(),context));
        return jo;
    }

    /**
     * Deserializzatore del budger
     * @param json
     * @param context
     * @return budget deseriliazzato
     * @throws JsonParseException
     */
    public IBudget<ITag> deserializeBudget(JsonElement json, JsonDeserializationContext context) throws JsonParseException {
        IBudget<ITag> budget = new Budget<>();
        for(JsonElement je : json.getAsJsonArray()) {
            String tagName = context.deserialize(je.getAsJsonObject().get("TagName"),String.class);
            String tagDescription = context.deserialize(je.getAsJsonObject().get("TagDescription"),String.class);
            double value = context.deserialize(je.getAsJsonObject().get("Value"),Double.class);
            ITag tag = this.ledger.getTags().stream()
                    .filter(t->t.getNome().equals(tagName))
                    .filter(t->t.getDescription().equals(tagDescription))
                    .findAny()
                    .get();
            budget.addBudgetType(tag,value);
        }
        return budget;
    }

    /**
     * Serializzatore del budget
     * @param src
     * @param context
     * @return budget serializzati
     */
    public JsonElement serializeBudget(IBudget<ITag> src, JsonSerializationContext context) {
        JsonArray ja = new JsonArray();
        for(ITag t : src.getBudgetMap().keySet()) {
            JsonObject jo = new JsonObject();
            jo.add("TagName", context.serialize(t.getNome()));
            jo.add("TagDescription", context.serialize(t.getDescription()));
            jo.add("Value",context.serialize(src.getValue(t)));
            ja.add(jo);
        }
        return ja;
    }

    /**
     * serializzazione dei tag
     * @param src
     * @param context
     * @return ritorna un array di elementi contenete tutti i tag presenti
     */
    private JsonElement tagsSerializer(List<ITag> src, JsonSerializationContext context){
        JsonArray ja = new JsonArray();
        src.stream().forEach(t->ja.add(tagSerializer(t, context)));
        return ja;
    }

    /**
     * Serializzatore della lista delle transazioni
     * @param src
     * @param context
     * @return riotrna un array di elementi contenente tutte le transazioni presenti
     */
    private JsonElement transactionsSerializer(List<ITransazione> src, JsonSerializationContext context){
        JsonArray ja = new JsonArray();
        src.stream().forEach(t->ja.add(serializeTransazione(t,context)));
        return ja;
    }

    /**
     * Serializzatore degli account
     * @param src
     * @param context
     * @return array json contenente tutti gli account serializzati
     */
    private JsonElement accountsSerializer(List<IAccount> src, JsonSerializationContext context) {
        JsonArray ja = new JsonArray();
        src.stream().forEach(a->ja.add(serializeAccount(a,context)));
        return ja;
    }

    /**
     * Serializzatore di un tag
     * @param src
     * @param context
     * @return tag serializzato
     */
    public JsonElement tagSerializer(ITag src, JsonSerializationContext context) {
        JsonObject jo = new JsonObject();
        jo.add("Name", context.serialize(src.getNome()));
        jo.add("Description", context.serialize(src.getDescription()));
        return jo;
    }

    /**
     * Serielizzatore di un account da salvare dentro il file json.
     * @param src
     * @param context
     * @return account serializzato
     */
    public JsonElement serializeAccount(IAccount src, JsonSerializationContext context) {
        JsonObject jo = new JsonObject();
        jo.add("ID", context.serialize(src.getID()));
        jo.add("Name", context.serialize(src.getNameAccount()));
        jo.add("Description", context.serialize(src.getDescriptionAccount()));
        jo.add("Type", context.serialize(src.getTypeAccount()));
        jo.add("OpeningBalance", context.serialize(src.getBalanceOfAccount()));
        return jo;
    }

    /**
     * Serializzatore di una transazione da salvare dentro il file json.
     * @param src
     * @param context
     * @return transazione serializzata
     */
    public JsonElement serializeTransazione(ITransazione src, JsonSerializationContext context) {
        JsonObject jo = new JsonObject();
        jo.add("ID", context.serialize(src.getID()));
        jo.add("Date", context.serialize(src.getData(), LocalDate.class));
        jo.add("Pagata", context.serialize(src.getPagata()));
        jo.add("Movements", movementsSerializer(src.movements(),context));
        jo.add("Tag", tagsSerializer(src.tags(),context));
        jo.add("Description", context.serialize(src.getDescription()));
        return jo;
    }

    /**
     * Serializzatore di una lista di movimenti
     * @param src
     * @param context
     * @return lista di movimenti serializzati
     */
    private JsonElement movementsSerializer(List<IMovement> src, JsonSerializationContext context){
        JsonArray ja = new JsonArray();
        src.stream().forEach(m->ja.add(serializeMovement(m,context)));
        return ja;
    }

    /**
     * Serializzatore di un movimento
     * @param src
     * @param context
     * @return movimento serializzato
     */
    public JsonElement serializeMovement(IMovement src, JsonSerializationContext context) {
        JsonObject jo = new JsonObject();
        jo.add("ID", context.serialize(src.getID()));
        jo.add("Type",context.serialize(src.getTipo(), MovementType.class));
        jo.add("Amount", context.serialize(src.getAmount()));
        jo.add("ID Account", context.serialize(src.getAccount().getID()));
        jo.add("Tag", tagsSerializer(src.tags(), context));
        jo.add("Description", context.serialize(src.getDescription()));
        return jo;
    }
    /**
     * Metodo responsabile della deserializzazione di un JsonElemant in una serie di Transazioni.
     * @param json JsonElement da deserializzare.
     * @param context
     * @return Serie di Transazioni deserializzati.
     */
    private List<ITransazione> transactionsDeserialize(JsonElement json, JsonDeserializationContext context){
        List<ITransazione> transactions = new ArrayList<>();
        for(JsonElement je : json.getAsJsonArray())
            transactions.add(transactionsDeserializeElement(je,context));
        return transactions;
    }

    /**
     * Deserializzatore dei tag per i movimenti e le transazioni
     * @param json
     * @param context
     * @return lista di tag del movimento o della transazione
     */
    private List<ITag> tagsDeserializerMovementAndTransaction(JsonElement json, JsonDeserializationContext context){
        List<ITag> tags= new ArrayList<>();
        for(JsonElement je : json.getAsJsonArray()) {
            tags = new ArrayList<>();
            JsonObject jsonObj = je.getAsJsonObject();
            String name = jsonObj.get("Name").getAsString();
            String description = jsonObj.get("Description").getAsString();
            if(ledger.getATag(name,description)!= null)
                tags.add(ledger.getATag(name,description));
        }
        return tags;
    }

    /**
     * Deserializzatore di un elemento delle transazioni
     * @param json
     * @param context
     * @return transazione deserializzata
     * @throws JsonParseException
     */
    public ITransazione transactionsDeserializeElement(JsonElement json,JsonDeserializationContext context) throws JsonParseException{
        JsonObject jo = json.getAsJsonObject();
        int id=jo.get("ID").getAsInt();
        LocalDate data = context.deserialize(jo.get("Date"), LocalDate.class);
        String description= jo.get("Description").getAsString();
        boolean pagata=jo.get("Pagata").getAsBoolean();
        List<ITag> tagList=new ArrayList<>();
        if(tagsDeserializerMovementAndTransaction(jo.get("Tag"), context) != null){
            tagList.addAll(tagsDeserializerMovementAndTransaction(jo.get("Tag"), context));
        }
        ITransazione trans = IFactory.generateTransaction(id,data,tagList,description,pagata);
        trans.addMovementList(movementsDeserialize(json.getAsJsonObject().get("Movements"),context,trans));
        trans.adjustTotalCost();
        return trans;


    }

    /**
     * Deserializzazione dei movimenti
     * @param json
     * @param context
     * @param t
     * @return ritorna la lista dei movimenti deserializzati
     */
    private List<IMovement> movementsDeserialize(JsonElement json, JsonDeserializationContext context, ITransazione t) {
        List<IMovement> movements = new ArrayList<>();
        for (JsonElement je : json.getAsJsonArray()) {
            movements.add(movementDeserialize(je,context,t));
        }
        return movements;
    }

    /**
     * Deserializzatore di un movimento
     * @param json
     * @param context
     * @param t
     * @return movimento deserializzato
     */
    private IMovement movementDeserialize(JsonElement json, JsonDeserializationContext context,ITransazione t){
        JsonObject jo =json.getAsJsonObject();
        int ID = jo.get("ID").getAsInt();
        MovementType type = MovementType.valueOf(jo.get("Type").getAsString());
        Double amount = jo.get("Amount").getAsDouble();
        String description = jo.get("Description").getAsString();
        List<ITag> tag = new ArrayList<>();
        if(tagsDeserializerMovementAndTransaction(jo.get("Tag"), context) != null){
            tag.addAll(tagsDeserializerMovementAndTransaction(jo.get("Tag"), context));
        }
        int IDAccount=jo.get("ID Account").getAsInt();
        IAccount a=ledger.getAccountForID(IDAccount);
        IMovement mov= IFactory.generateMovement(ID,description,type,amount,a,tag,t);
        ledger.getAccountForID(IDAccount).addMovementDeserialized(mov);
        for (int i=0; i<tag.size();i++){
            tag.get(i).addMovement(mov);
        }
        return mov;
    }


    /**
     * Metodo responsabile della deserializzazione di un JsonElemant in una serie di Account.
     * @param json JsonElement da deserializzare.
     * @param context
     * @return Lista di Accounts deserializzati.
     */
    private List<IAccount> accountsDeserialize(JsonElement json, JsonDeserializationContext context){
        List<IAccount> accounts = new ArrayList<>();
        for(JsonElement je : json.getAsJsonArray())
            accounts.add(accountDeserialize(je,context));
        return accounts;
    }

    /**
     * Metodo di deserializzazioni di un'account
     * @param json
     * @param context
     * @return account generato e inserito nel nuovo ledger
     */
    private IAccount accountDeserialize(JsonElement json, JsonDeserializationContext context){
        JsonObject jo =json.getAsJsonObject();
        int id=jo.get("ID").getAsInt();
        String name = jo.get("Name").getAsString();
        String description = jo.get("Description").getAsString();
        AccountType type = AccountType.valueOf(jo.get("Type").getAsString());
        //AccountType type = jo.get("Type");
        Double amount=jo.get("OpeningBalance").getAsDouble();
        return IFactory.generateAccount(id,name,description,type,amount);
    }
    /**
     * Metodo che deserializza i tag letti dal file json
     * @param json
     * @param context
     * @return ritorna lista di tag deserializzati
     */
    private List<ITag> tagsDeserialize(JsonElement json, JsonDeserializationContext context){
        List<ITag> tags = new ArrayList<>();
        for(JsonElement je : json.getAsJsonArray())
            tags.add(tagDeserializeElement(je));
        return tags;
    }

    /**
     * Deserializzatore di un elemnto di un tag
     * @param json
     * @return tag generato
     * @throws JsonParseException
     */
    public ITag tagDeserializeElement(JsonElement json) throws JsonParseException {
        JsonObject jo = json.getAsJsonObject();
        String name = jo.get("Name").getAsString();
        String description = jo.get("Description").getAsString();
        return IFactory.generateTag(name,description);
    }

}
