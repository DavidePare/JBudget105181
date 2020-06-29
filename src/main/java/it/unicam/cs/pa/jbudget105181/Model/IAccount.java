package it.unicam.cs.pa.jbudget105181.Model;

import java.util.List;
import java.util.function.Predicate;

public interface IAccount {
	String getNameAccount();
	String getDescriptionAccount();
	int getIDAccount();
	double getOpeningBalance();
	double getBalanceOfAccount();
	List<IMovement> getMovementsOfAccount();
	void addMovement(IMovement mov);
	List<IMovement> getMovementsOfAccount(Predicate<IMovement> p);
	AccountType getTypeAccount();
	void removeMovementAccount(IMovement x);
}
