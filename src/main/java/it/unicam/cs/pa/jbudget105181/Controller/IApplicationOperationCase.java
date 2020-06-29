package it.unicam.cs.pa.jbudget105181.Controller;

import it.unicam.cs.pa.jbudget105181.Model.ILedger;

public interface IApplicationOperationCase {
	void caseOneInsertTag(ILedger ledger);
	void caseTwoInsertAccount(ILedger ledger);
	void caseThreeInsertTransaction(ILedger ledger);
	void caseFourAddMovementToTransaction(ILedger ledger);
	void caseFiveInsertMovement(ILedger ledger);
	void caseSevenPrintTransaction(ILedger ledger);

	void caseEightPrintAccount(ILedger ledger);
	void caseNinePrintTag(ILedger ledger); 
}
