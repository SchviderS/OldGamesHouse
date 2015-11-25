package br.univel.rest;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.junit.Test;

import br.univel.model.Produto;

public class VendaCarrinhoTest {
	
	private Client createClient() {
		return ClientBuilder.newBuilder()
				.register(JacksonJaxbJsonProvider.class).build();
	}
	
	@Test
	public void testeGravacaoCarrinhoVenda(){ 
		Produto p1 = buscaProduto(7);
		Produto p2 = buscaProduto(8);
	
		assertEquals(addProdutoQtd(p1.getId(), 2), true);
		assertEquals(addProdutoQtd(p2.getId(), 1), true);
		
		assertEquals(finalizarCarrinho(), true);
		
	}

	public Produto buscaProduto(long id){		
		Client webClientLeitura = createClient();
		
		WebTarget destinoLeitura = webClientLeitura
				.target("http://localhost:8080/ecommerce-old-games-house/rest/produtos/"+id);
		
		Response respostaLeitura = destinoLeitura
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).get();
		
		Produto p = respostaLeitura.readEntity(Produto.class);
		return p;
	}
	

	private boolean addProdutoQtd(Long idProduto, int qtd){
		Client webClientLeitura = createClient();
		
		WebTarget destinoLeitura = webClientLeitura
				.target("http://localhost:8080/ecommerce-old-games-house/rest/adicionar/"+idProduto+"/"+qtd);
		
		Response respostaLeitura = destinoLeitura.request().get();
		assertEquals(Status.CREATED.getStatusCode(),
				respostaLeitura.getStatus());
		
		return Status.CREATED.getStatusCode() == respostaLeitura.getStatus();
	}
	
	private boolean finalizarCarrinho() {

		Client webClientLeitura = createClient();
		
		WebTarget destinoLeitura = webClientLeitura
				.target("http://localhost:8080/ecommerce-old-games-house/rest/finalizar");
		
		Response respostaLeitura = destinoLeitura.request().get();
		assertEquals(Status.CREATED.getStatusCode(),
				respostaLeitura.getStatus());
		
		return Status.CREATED.getStatusCode() == respostaLeitura.getStatus();
	}
}
