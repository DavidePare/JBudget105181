package it.unicam.cs.pa.jbudget105181.Model;

import java.util.List;
import java.util.function.Predicate;

public interface IAccount extends IUtility{
	String getNameAccount();
	String getDescriptionAccount();
	double getOpeningBalance();
	double getBalanceOfAccount();
	List<IMovement> getMovementsOfAccount();
	void addMovement(IMovement mov);
	List<IMovement> getMovementsOfAccount(Predicate<IMovement> p);
	AccountType getTypeAccount();
	void removeMovementAccount(IMovement x);

	void setName(String name);
	void setDescription(String description);
	void setType(AccountType type);
	void setConto(Double amount);

	void recalculateConto();
}
