import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class FindOperation {

	public static void main(String[] args) {
		
		@SuppressWarnings("deprecation")
		GraphDatabaseService database = new GraphDatabaseFactory().newEmbeddedDatabase("/Users/Leela/Desktop/test.graphdb");
		System.out.println("Database Running!");
		
		Result result = database.execute("MATCH (d:department) RETURN d, d.name");
		while(result.hasNext()) {
			Map<String,Object> next = result.next();
			String value = (String)next.get("d.name");
			System.out.println(value);
		}
		
		System.out.println("Database Shutdown");
		database.shutdown();
		System.out.println("Finished!!!");
	}

}
