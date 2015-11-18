package br.univel.beans;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.univel.model.Produto;
import java.io.Serializable;

@SessionScoped
@Named
public class Carrinho implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	List<Produto> produtos = new ArrayList<Produto>();

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public void addProduto(Produto p){
		produtos.add(p);
	}
	
	public void limpar(){
		produtos.clear();
	}
	
}