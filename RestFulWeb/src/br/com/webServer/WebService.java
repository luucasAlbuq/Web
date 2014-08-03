package br.com.webServer;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.*;

@Path("/post")
public class WebService {
	
	public static int sequenciaCliente;
	public static List<Cliente> clientes = new ArrayList<Cliente>();
	private String json;
	ObjectMapper mapper = new ObjectMapper();
	
	@DELETE
	@Path("/{id}")
	public void removerCliente(@PathParam("id") String id){
		Cliente c = searchClienteById(id);
		clientes.remove(c);
	}
	
	@PUT
	@Path("/{id}")
	public void editCliente(@PathParam("id") String id, @FormParam("novoNome") String nome){
		Cliente c = searchClienteById(id);
		c.setNome(nome);
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCliente(@PathParam("id") String id){
		Cliente c = searchClienteById(id);
		if(c==null){
			throw new WebApplicationException(Status.NOT_FOUND);
		}try{
			return mapper.writeValueAsString(c);
		}catch(IOException e){
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllClientes(){
		try{
			json = mapper.writeValueAsString(clientes);
		}catch(IOException e){
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		return json;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String creatCliente(@FormParam("nome") String nome){
		Cliente novoCliente = new Cliente(nome);
		sequenciaCliente++;
		novoCliente.setId(sequenciaCliente);
		clientes.add(novoCliente);
		throw new WebApplicationException(Status.OK);
	}
	
	private Cliente searchClienteById(String id) {
		for (Cliente c : clientes) {
			if (c.getId().toString().equals(id)) {
				return c;
			}
		}
		return null;
	}
	
	@GET
	@Path("/{id}/comment")
	@Produces(MediaType.APPLICATION_JSON)
	public String getComprasById(@PathParam("id") String id){
		Cliente c = searchClienteById(id);
		try{
			return mapper.writeValueAsString(c.getCompras());
		}catch(IOException e){
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}
	
	@POST
	@Path("/{id}/comment/{sequence}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCompra(@PathParam("id") String id, @PathParam("sequence") String sequence){
		Cliente c = searchClienteById(id);
		try{
			return mapper.writeValueAsString(c.getCompra(sequence));
		}catch(IOException e){
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}
	
	
	@POST
	@Path("/{id}/comment")
	@Produces(MediaType.APPLICATION_JSON)
	public String createCompra(@PathParam("id") String id,
			@FormParam("nome") String nome) {
		Cliente c = searchClienteById(id);
		c.addCompra(nome);
		return Status.ACCEPTED.toString();
	}
	
	@PUT
	@Path("/{id}/comment/{sequence}")
	public void editCompra(@PathParam("id")String id,
			@PathParam("sequence")String sequence,
			@FormParam("novaNome") String nome){
		searchClienteById(id).getCompra(sequence).setNome(nome);
	}
	
	@DELETE
	@Path("/{id}/comment/{sequence}")
	public void removerCompra(@PathParam("id") String id, 
			@PathParam("sequence") String sequence){
		searchClienteById(id).removerCompra(sequence);
	}
}
