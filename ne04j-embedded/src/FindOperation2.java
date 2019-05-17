import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class FindOperation2 {

	public static void main(String[] args) {
		
		@SuppressWarnings("deprecation")
		GraphDatabaseService database = new GraphDatabaseFactory().newEmbeddedDatabase("/Users/Leela/Desktop/test.graphdb");
		System.out.println("Database Running!");
		
		try(Transaction tx = database.beginTx()) {
			Result result = database.execute("MATCH (d:department) RETURN d, d.name");
			while(result.hasNext()) {
				Map<String,Object> row = result.next();
				Node value = (Node)row.get("d");
				
				System.out.println(value.getProperty("name"));
				for(Relationship next: value.getRelationships()) {
					System.out.println("This department has a relationship of type " + next.getType() + "to the node" + next.getOtherNode(value).getProperty("name"));
				}
			}
			tx.success();
		}
		
		//tx.success(); // This is a checkpoint operation.
		// tx.failure(); // This is a rollback operation.
		//tx.close(); //This will commit the transaction.
		
		System.out.println("Database Shutdown");
		database.shutdown();
		System.out.println("Finished!!!");
	}

}
