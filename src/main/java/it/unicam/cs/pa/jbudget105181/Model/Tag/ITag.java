package it.unicam.cs.pa.jbudget105181.Model.Tag;
import it.unicam.cs.pa.jbudget105181.Model.Budget.BudgetInterface;
import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;

import java.util.List;

public interface ITag extends BudgetInterface {
	String getDescription();
	String getNome();
	void addMovement(IMovement movement);
	void deleteMovement(IMovement delMovement);
	void deleteMovement(List<IMovement> delMovement);
	Double getAmount();
}
