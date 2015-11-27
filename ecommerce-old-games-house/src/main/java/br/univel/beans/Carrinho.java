package br.univel.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import br.univel.model.Produto;

@ApplicationScoped
public class Carrinho implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	HashMap<Long, Integer> map = new HashMap<Long ,Integer>();
	
	List<Produto> produtos = new ArrayList<Produto>();

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public synchronized void addProduto(Produto p, int qtd){
		boolean flag = false;
		map.put(p.getId(), qtd);
		
		if(!produtos.isEmpty())
			for(Produto prod : produtos){
				if(prod.getId() != p.getId()){
					map.put(p.getId(), qtd);
					flag = false;
				}else{
					map.replace(p.getId(), qtd);
					flag = true;
				}
			}
		else
			produtos.add(p);
		if(flag) produtos.remove(p);
	}
	
	public synchronized void limpar(){
		produtos.clear();
		map.clear();
	}
	
	public synchronized HashMap<Long, Integer> getMap(){
		return map;
	}
}