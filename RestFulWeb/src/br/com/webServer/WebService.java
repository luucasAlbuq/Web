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

import com.sun.research.ws.wadl.Response;

import java.io.IOException;
import java.util.*;

@Path("/post")
public class WebService {
	
	public static int sequenciaCliente;
	public static List<Post> posts = new ArrayList<Post>();
	private String json;
	ObjectMapper mapper = new ObjectMapper();
	
	@DELETE
	@Path("/{id}")
	public void removerPost(@PathParam("id") String id){
		Post c = searchPostById(id);
		posts.remove(c);
	}
	
	@PUT
	@Path("/{id}")
	public void editPost(@PathParam("id") String id, @FormParam("novaMsg") String msg){
		Post c = searchPostById(id);
		c.setMsg(msg);
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPost(@PathParam("id") String id){
		Post c = searchPostById(id);
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
	public String getAllPost(){
		
		try{
			json = mapper.writeValueAsString(posts);
		}catch(IOException e){
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		return json; 

	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String creatPost(@FormParam("msg") String msg){
		Post novoCliente = new Post(msg);
		sequenciaCliente++;
		novoCliente.setId(sequenciaCliente);
		posts.add(novoCliente);
		throw new WebApplicationException(Status.OK);
	}
	
	private Post searchPostById(String id) {
		for (Post c : posts) {
			if (c.getId().toString().equals(id)) {
				return c;
			}
		}
		return null;
	}
	
	@GET
	@Path("/{id}/comment")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCommentById(@PathParam("id") String id){
		Post c = searchPostById(id);
		try{
			return mapper.writeValueAsString(c.getComments());
		}catch(IOException e){
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}
	
	@GET
	@Path("/{id}/comment/{sequence}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getComment(@PathParam("id") String id, @PathParam("sequence") String sequence){
		Post c = searchPostById(id);
		try{
			return mapper.writeValueAsString(c.getComment(sequence));
		}catch(IOException e){
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}
	
	
	@POST
	@Path("/{id}/comment")
	@Produces(MediaType.APPLICATION_JSON)
	public String createComment(@PathParam("id") String id,
			@FormParam("msg") String msg) {
		Post c = searchPostById(id);
		c.addComment(msg);
		return Status.ACCEPTED.toString();
	}
	
	@PUT
	@Path("/{id}/comment/{sequence}")
	public void editComment(@PathParam("id")String id,
			@PathParam("sequence")String sequence,
			@FormParam("novaMsg") String msg){
		searchPostById(id).getComment(sequence).setMsg(msg);
	}
	
	@DELETE
	@Path("/{id}/comment/{sequence}")
	public void removerComment(@PathParam("id") String id, 
			@PathParam("sequence") String sequence){
		searchPostById(id).removeComment(sequence);
	}
}
