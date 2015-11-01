package br.univel.beans;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.univel.model.Produto;

@SessionScoped
@Named
public class Carrinho
{
	List<Produto> produtos;

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	
}