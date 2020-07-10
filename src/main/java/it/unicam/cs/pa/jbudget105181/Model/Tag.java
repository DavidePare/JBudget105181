package it.unicam.cs.pa.jbudget105181.Model;

public class Tag implements ITag {
	private String descrizione="";
	private String nome;
	public Tag(String nome, String descrizione) {
		if(nome.isEmpty() || descrizione.isEmpty() ) throw new IllegalArgumentException("You don't insert all data!");
		this.descrizione=descrizione;
		this.nome=nome;
	}
	// Overload nel caso l'utente decidesse di omettere la descrizione.
	public Tag(String nome) {
		this.nome=nome;
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return descrizione;
	}

	@Override
	public String getNome() {
		// TODO Auto-generated method stub
		return nome;
	}

}
