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
	
//	HashMap<Long, Integer> map = new HashMap<Long ,Integer>();
	HashMap<Produto, Integer> map = new HashMap<Produto, Integer>();
	
	List<Produto> produtos = new ArrayList<Produto>();

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public synchronized void addProduto(Produto p, int qtd){
//		boolean flag = false;
//		map.put(p.getId(), qtd);
//		
//		if(!produtos.isEmpty())
//			for(Produto prod : produtos){
//				if(prod.getId() != p.getId()){
//					map.put(p.getId(), qtd);
//					flag = false;
//				}else{
//					map.replace(p.getId(), qtd);
//					flag = true;
//				}
//			}
//		else
//			produtos.add(p);
//		if(flag) produtos.remove(p);
		
		boolean flag = false;
		this.produtos.add(p);
		for(Produto prd : produtos){
			if(prd.getId() == p.getId()){
				Integer qtMap = map.get(prd);
				if(qtMap == null)
					map.put(prd, qtd);
				else{
					map.replace(prd, qtd);
					flag = true;
				}
			}
		}
		if(flag) produtos.remove(p);
	}
	
	public synchronized void limpar(){
		produtos.clear();
		map.clear();
	}
	
	public synchronized HashMap<Produto, Integer> getMap(){
		return map;
	}
}