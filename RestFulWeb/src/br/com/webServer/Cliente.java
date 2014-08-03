package br.com.webServer;

import java.util.ArrayList;
import java.util.List;



public class Cliente {
	
	private Integer id;
	private String nome;
	private List<Compra> compras;
	
	public Cliente(String nome){
		this.nome = nome;
		compras = new ArrayList<Compra>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}
	
	public void addCompra(String nome){
		getCompras().add(new Compra(this.compras.size()+1, nome));
	}
	
	public Compra getCompra(String codigo){
		for(Compra comp : compras){
			if(comp.getCodigo().toString().equals(comp)){
				return comp;
			}
		}
		return null;
	}
	
	public void removerCompra(String codigo){
		Compra c = getCompra(codigo);
		compras.remove(c);
	}
	
	@Override
	public String toString() {
		return "Cliente N: " + getId() + "\n Nome: " + this.nome;
	}
	
}
