package it.unicam.cs.pa.jbudget105181.Model.Movement;
import it.unicam.cs.pa.jbudget105181.Model.Account.IAccount;
import it.unicam.cs.pa.jbudget105181.Model.IUtility;
import it.unicam.cs.pa.jbudget105181.Model.Movement.MovementType;
import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;
import it.unicam.cs.pa.jbudget105181.Model.Transaction.ITransazione;

import java.util.List;
public interface IMovement extends IUtility {

	String getDescription();
	double getAmount();
	IAccount getAccount();
	List<ITag> tags();
	void addTag(ITag t);
	//void removeTag(ITag t);
	int getIDTransazione();
	MovementType getTipo();
	//LocalDate getDate();
	//void modifyMovement(int ID, String description, );
	void removeTag(List<ITag> t);
	int getIDAccount();
	void setID(int idMovement);
	ITransazione getTransaction();
	void setTransaction(ITransazione transaction);
}
