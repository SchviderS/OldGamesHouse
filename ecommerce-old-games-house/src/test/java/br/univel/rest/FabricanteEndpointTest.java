package br.univel.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

import br.univel.model.Fabricante;

import org.junit.Test;

import static org.junit.Assert.*;

public class FabricanteEndpointTest {
	private Client createFabricante() {
			return ClientBuilder.newBuilder()
					.register(JacksonJaxbJsonProvider.class).build();
		}
	
		@Test
		public void testeGravacaoLeituraCliente() {
	
			Fabricante fabricante = new Fabricante();
			fabricante.setNome("Nintendo");
			fabricante.setLogo("nintendo.logo");
	
			String urlFabricanteCriado;
			{
				Entity<Fabricante> fabricanteJson = Entity.json(fabricante);
	
				Client webFabricanteGravacao = createFabricante();
	
				WebTarget destinoGravacao = webFabricanteGravacao
						.target("http://localhost:8080/ecommerce-old-games-house/rest/fabricantes");
	
				Response respostaGravacao = destinoGravacao
						.request(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).post(fabricanteJson);
	
				assertEquals(Status.CREATED.getStatusCode(),
						respostaGravacao.getStatus());
	
				urlFabricanteCriado = respostaGravacao.getHeaderString("Location");
			}
	
			Fabricante fabricanteGravado;
			{
				Client webClientLeitura = createFabricante();
	
				WebTarget destinoLeitura = webClientLeitura
						.target(urlFabricanteCriado);
	
				Response respostaLeitura = destinoLeitura
						.request(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).get();
	
				fabricanteGravado = respostaLeitura.readEntity(Fabricante.class);
	
				respostaLeitura.getHeaders().entrySet().stream().forEach(t -> {
					System.out.println(t.getKey());
					t.getValue().forEach(e -> System.out.println("\t" + e));
				});
			}
	
			assertNotNull(fabricanteGravado.getId());
			assertNotNull(fabricanteGravado.getVersion());
	
			assertEquals(fabricante.getNome(), fabricanteGravado.getNome());
			assertEquals(fabricante.getLogo(), fabricanteGravado.getLogo());
	
		}
}

