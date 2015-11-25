package br.univel.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.junit.Test;

import br.univel.model.Fabricante;
import br.univel.model.Produto;


public class ProdutoEndpointTest {
	
	private Client createProduto() {
			return ClientBuilder.newBuilder()
					.register(JacksonJaxbJsonProvider.class).build();
		}

		@Test
		public void testeGravacaoLeituraCliente() {
			Produto produto = new Produto();
			produto.setNome("Zelda");
			produto.setDescricao("Ocarina of time");
			produto.setFabricante(buscarFabricante(Long.valueOf(1)));
			produto.setImagem("zelda.img");
			produto.setValor(new BigDecimal("130.00"));
			
			String urlProdutoCriado;
			{
				Entity<Produto> produtoJson = Entity.json(produto);
	
				Client webProdutoGravacao = createProduto();
	
				WebTarget destinoGravacao = webProdutoGravacao
						.target("http://localhost:8080/ecommerce-old-games-house/rest/produtos");
	
				Response respostaGravacao = destinoGravacao
						.request(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).post(produtoJson);
	
				assertEquals(Status.CREATED.getStatusCode(),
						respostaGravacao.getStatus());
	
				urlProdutoCriado = respostaGravacao.getHeaderString("Location");
			}
	
			Produto produtoGravado;
			{
				Client webClientLeitura = createProduto();
	
				WebTarget destinoLeitura = webClientLeitura
						.target(urlProdutoCriado);
	
				Response respostaLeitura = destinoLeitura
						.request(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).get();
	
				produtoGravado = respostaLeitura.readEntity(Produto.class);
	
				respostaLeitura.getHeaders().entrySet().stream().forEach(t -> {
					System.out.println(t.getKey());
					t.getValue().forEach(e -> System.out.println("\t" + e));
				});
			}
	
			assertNotNull(produtoGravado.getId());
			assertNotNull(produtoGravado.getVersion());
	
			assertEquals(produto.getNome(), produtoGravado.getNome());
			assertEquals(produto.getImagem(), produtoGravado.getImagem());
			assertEquals(produto.getDescricao(), produtoGravado.getDescricao());
			assertEquals(produto.getValor(), produtoGravado.getValor());
			assertEquals(produto.getFabricante(), produtoGravado.getFabricante());
	
		}

		private Fabricante buscarFabricante(Long id) {
			Client webClientLeitura = createProduto();
			
			WebTarget destinoLeitura = webClientLeitura
					.target("http://localhost:8080/ecommerce-old-games-house/rest/fabricantes/"+id);

			Response respostaLeitura = destinoLeitura
					.request(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON).get();

			Fabricante f = respostaLeitura.readEntity(Fabricante.class);
			return f;
		}
}

