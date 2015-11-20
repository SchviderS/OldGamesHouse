package br.univel.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

import br.univel.model.Cliente;

import org.junit.Test;

import static org.junit.Assert.*;

public class ClienteEndpointTest {
	private Client createClient() {
		return ClientBuilder.newBuilder()
				.register(JacksonJaxbJsonProvider.class).build();
	}

	@Test
	public void testeGravacaoLeituraCliente() {

		Cliente cliente = new Cliente();
		cliente.setNome("Alexandre");
		cliente.setTelefone("99792929");
		cliente.setEmail("erdnaxelaschvi@gmail.com");
		cliente.setEndereco("Rua Blalbalba, 3232");

		String urlClienteCriado;
		{
			Entity<Cliente> clienteJson = Entity.json(cliente);

			Client webClientGravacao = createClient();

			WebTarget destinoGravacao = webClientGravacao
					.target("http://localhost:8080/ecommerce-old-games-house/rest/clientes");

			Response respostaGravacao = destinoGravacao
					.request(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON).post(clienteJson);

			assertEquals(Status.CREATED.getStatusCode(),
					respostaGravacao.getStatus());

			urlClienteCriado = respostaGravacao.getHeaderString("Location");
		}

		Cliente clienteGravado;
		{
			Client webClientLeitura = createClient();

			WebTarget destinoLeitura = webClientLeitura
					.target(urlClienteCriado);

			Response respostaLeitura = destinoLeitura
					.request(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON).get();

			clienteGravado = respostaLeitura.readEntity(Cliente.class);

			respostaLeitura.getHeaders().entrySet().stream().forEach(t -> {
				System.out.println(t.getKey());
				t.getValue().forEach(e -> System.out.println("\t" + e));
			});
		}

		assertNotNull(clienteGravado.getId());
		assertNotNull(clienteGravado.getVersion());

		assertEquals(cliente.getNome(), clienteGravado.getNome());
		assertEquals(cliente.getTelefone(), clienteGravado.getTelefone());

	}
}
