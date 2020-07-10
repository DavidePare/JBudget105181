package it.unicam.cs.pa.jbudget105181.Model;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

public interface ITransazione extends IUtility{

	ArrayList<IMovement> movements();
	List<ITag> tags(); 
	void addTag(ITag nuovoTag);
	boolean removeTag(ITag eliminatag);
	LocalDate getData();
	void setData(LocalDate datanuova);
	double getTotalAmount();
//	ArrayList<LocalDate> getDate();
	void addMovement(IMovement addMov);
	void removeMovement(IMovement m);
	boolean getPagata();
	int getNumMov();
	String getDescription();
	void setDescription(String description);
	void addMovementList(List<IMovement> lMovent);
}
