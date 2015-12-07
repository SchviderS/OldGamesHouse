package br.univel.rest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.univel.beans.Carrinho;
import br.univel.dao.ClienteDao;
import br.univel.dao.ItemVendaDao;
import br.univel.dao.ProdutoDao;
import br.univel.dao.VendaDao;
import br.univel.model.ItemVenda;
import br.univel.model.Produto;
import br.univel.model.Venda;

@Stateful
@Path("/cart")
public class CartEndpoint implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Carrinho carrinho;
	
	@Inject 
	ItemVendaDao ivDao;
	
	@Inject
	VendaDao vendaDao;
	
	@Inject
	ProdutoDao produtoDao;
	
	@Inject
	ClienteDao clienteDao;
	
	@GET
	@Path("/adicionar/{id:[0-9][0-9]*}/{qtd:[0-9][0-9]*}")
	public synchronized Response adicionarProduto(@PathParam("id") long id, @PathParam("qtd") int qtd){
		
		System.out.println("ID = "+id);
		System.out.println("QTD = "+qtd);
		
		Produto p = produtoDao.findById(id);
		System.out.println(p);
		
		carrinho.addProduto(p,qtd);
		
		System.out.println("Produtos Adicionados - "+carrinho.getProdutos());
		return Response.ok().build();
	}
	
	@GET
	@Path("/limparCarrinho")
	public synchronized Response limpar(){
		carrinho.limpar();
		return Response.ok().build();
	}
	
	@GET
	@Path("/finalizar/{idCliente:[0-9][0-9]*}")
	public synchronized Response finalizar(@PathParam("idCliente") long idCliente){
		Venda venda = new Venda();
		Set<ItemVenda> lista = new HashSet<>();
		double total = 0;
		Iterator<Produto> keys = carrinho.getMap().keySet().iterator();
		
		System.out.println("Finalizar cart - "+carrinho.getProdutos());
		System.out.println("HashMap - "+ carrinho.getMap().toString());
		
		while(keys.hasNext()){
//			Long idProduto = keys.next();
			Produto p = keys.next();
			ItemVenda iv = new ItemVenda();
//			iv.setIdProduto(idProduto.intValue());
//			iv.setValor(produtoDao.findById(idProduto).getValor());
//			System.out.println("PRODUTO DO FOR = "+idProduto);
//			Integer qtd = carrinho.getMap().get(idProduto);
			
			iv.setIdProduto(p.getId().intValue());
			iv.setValor(p.getValor());
			System.out.println("PRODUTO DO FOR = "+p.getId());
			Integer qtd = carrinho.getMap().get(p);
			
			System.out.println("QUANTIDADE RESGATADA = "+qtd);
			iv.setQuantidade(qtd);
			lista.add(iv);
			ivDao.create(iv);
			
//			total += produtoDao.findById(idProduto).getValor().doubleValue() * iv.getQuantidade();
			total += p.getValor().doubleValue() * iv.getQuantidade();
		}
		
		venda.setCliente(clienteDao.findById(idCliente));
		venda.setItens(lista);
		java.util.Date utilDate = new java.util.Date();
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	    venda.setData(sqlDate);
		venda.setTotal(new BigDecimal(total));
		vendaDao.create(venda);
		return Response.ok().build();
	}
	
}
