package br.univel.rest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.univel.beans.Carrinho;
import br.univel.dao.ProdutoDao;
import br.univel.model.ItemVenda;
import br.univel.model.Produto;
import br.univel.model.Venda;

@Stateful
@Path("/cart")
public class CartEndpoint implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	HashMap<Long,Integer> map = new HashMap<Long,Integer>();
	
	@Inject
	private Carrinho carrinho;
	
	@Inject
	VendaEndpoint vendaEp;
	
	@Inject
	ProdutoDao dao;
	
	@GET
	@Path("/adicionar/{id:[0-9][0-9]*}/{qtd:[0-9][0-9]*}")
	public Response adicionarProduto(@PathParam("id") long id, @PathParam("qtd") int qtd){
		
		System.out.println("ID = "+id);
		System.out.println("QTD = "+qtd);
		
//		ProdutoDao dao = new ProdutoDao();
		
		Produto p = dao.findById(id);
		
		System.out.println(p.toString());
		
		for(Produto prod : carrinho.getProdutos()){
			if(prod.getId() == id){
				map.replace(prod.getId(), qtd);
				
			}else{
				carrinho.addProduto(p);
				map.put(p.getId(), qtd);
				
			}
		}
		return Response.ok().build();
	}
	
	@GET
	@Path("/limparCarrinho")
	public Response limpar(){
		carrinho.limpar();
		map.clear();
		return Response.ok().build();
	}
	
	@GET
	@Path("/finalizar")
	public Response finalizar(){
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
		return Response.ok().build();
	}
	
}
