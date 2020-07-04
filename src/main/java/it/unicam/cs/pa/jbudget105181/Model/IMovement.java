package it.unicam.cs.pa.jbudget105181.Model;
import java.util.List;
public interface IMovement extends IUtility{

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
	
	
}
