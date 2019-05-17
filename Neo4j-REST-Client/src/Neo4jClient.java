import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;


public class Neo4jClient 
{
	private static final String SERVER_ROOT_URI = "http://localhost:7474/db/data";

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException, SQLException, ClassNotFoundException
	{
		Client client = ClientBuilder.newClient();

		// Need to provide authentication token with request
		HttpAuthenticationFeature auth = HttpAuthenticationFeature.basicBuilder().credentials("neo4j", "Leela@17").build();
		client.register(auth);
		
		/*WebTarget web = client.target (SERVER_ROOT_URI);
		String response = web.request().accept(MediaType.APPLICATION_JSON).get(String.class);
		System.out.println(response);*/
		
		WebTarget web = client.target (SERVER_ROOT_URI + "/labels");
		String response = web.request().accept(MediaType.APPLICATION_JSON).get(String.class);
		System.out.println(response);
		
		// now run some cypher - POST to root/transaction/commit
		web=client.target(SERVER_ROOT_URI + "/transaction/commit");
		String query = "MATCH (n:department) RETURN n";
		String queryJson = "{\"statements\" : [ {\"statement\" : \"" +query + "\"} ]}";

		Response queryResult = web.request().post(Entity.entity(queryJson, MediaType.APPLICATION_JSON));
		String resultAsJsonString = queryResult.readEntity(String.class);
		System.out.println(resultAsJsonString);
		System.out.println("Status " + queryResult.getStatus());

		
		// how to parse that very messy JSON?
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readValue(resultAsJsonString, JsonNode.class);

		List<JsonNode> rows = root.findValues("row");

		// this annoyingly gives us an array....of arrays...
		for (JsonNode next : rows)
		{
			if (next.isArray())
			{
				ArrayNode array = (ArrayNode)next;
				Department dept = mapper.treeToValue(array.get(0), Department.class);
				System.out.println(dept);
			}
		}
				
	}
}
