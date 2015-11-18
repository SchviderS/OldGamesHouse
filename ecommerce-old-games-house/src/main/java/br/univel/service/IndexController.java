package br.univel.service;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;

import br.univel.model.Produto;
import br.univel.rest.ProdutoEndpoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateless
@LocalBean
public class IndexController implements Serializable
{

   private static final long serialVersionUID = -1L;
   
   private List<Produto> produtos = new ProdutoEndpoint().listAll(null, null);

   public List<Produto> getProdutos() {
	   return produtos;
   }
   
   public Produto getProduto(int id){
	   Produto prod = null;
	   for(Produto p: produtos){
		   if(p.getId() == id)
			   prod = p;
	   }
	   return prod;
   }
   
}