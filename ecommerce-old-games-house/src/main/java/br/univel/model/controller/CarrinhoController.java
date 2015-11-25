package br.univel.model.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import br.univel.beans.Carrinho;
import br.univel.model.ItemVenda;
import br.univel.model.Produto;
import br.univel.model.Venda;
import br.univel.rest.ProdutoEndpoint;
import br.univel.rest.VendaEndpoint;


@RequestScoped
public class CarrinhoController {
	
	HashMap<Long,Integer> map = new HashMap<Long,Integer>();
	
	@Inject
	private Carrinho carrinho;
	
	@Inject
	private ProdutoEndpoint pe;
	
	@Inject
	VendaEndpoint vendaEp;
	
	@Path("/adicionar/{id:[0-9][0-9]*}/{qtd:[0-9][0-9]*}")
	public void adicionarProduto(@PathParam("id") long id, @PathParam("qtd") int qtd){
		Produto p = pe.findById(id).readEntity(Produto.class);
		
		for(Produto prod : carrinho.getProdutos()){
			if(prod.getId() == id)
				map.replace(prod.getId(), qtd);
			else{
				carrinho.addProduto(p);
				map.put(p.getId(), qtd);
			}
		}
	}
	
	@Path("/limparCarrinho")
	public void limpar(){
		carrinho.limpar();
		map.clear();
	}
	
	@Path("/finalizar")
	public void finalizar(){
		Venda venda = new Venda();
		Set<ItemVenda> lista = new HashSet<>();
		double total = 0;
		
		for(Produto p : carrinho.getProdutos()){
			ItemVenda iv = new ItemVenda();
			iv.setIdProduto(p.getId().intValue());
			iv.setValor(p.getValor());
			iv.setQuantidade(map.get(p.getId()));
			lista.add(iv);
			total += p.getValor().doubleValue() * iv.getQuantidade();
		}
		
		venda.setItens(lista);
		java.util.Date utilDate = new java.util.Date();
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	    venda.setData(sqlDate);
		venda.setTotal(new BigDecimal(total));
		vendaEp.create(venda);
	}

}
