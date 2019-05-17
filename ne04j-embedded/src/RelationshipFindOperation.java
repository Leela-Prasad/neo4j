import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class RelationshipFindOperation {

	public static void main(String[] args) {
		
		@SuppressWarnings("deprecation")
		GraphDatabaseService database = new GraphDatabaseFactory().newEmbeddedDatabase("/Users/Leela/Desktop/test.graphdb");
		System.out.println("Database Running!");
		
		try(Transaction tx = database.beginTx()) {
			Result result = database.execute("MATCH () -[r]- () RETURN r");
			while(result.hasNext()) {
				Map<String,Object> row = result.next();
				Relationship value = (Relationship)row.get("r");
				
				String type = value.getType().toString();
				Node endNode = value.getEndNode();
				Node startNode = value.getOtherNode(endNode);
				System.out.println(startNode.getProperty("name") + "-" + type + "->" + endNode.getProperty("name"));
			}
			tx.success();
		}
		
		System.out.println("Database Shutdown");
		database.shutdown();
		System.out.println("Finished!!!");
	}

}
