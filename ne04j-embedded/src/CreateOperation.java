import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class CreateOperation {

	enum DatabaseLabels implements Label {
		// These labels case sensitive and should match
		// lables in graph db
		employee, group, department;
	}
	public static void main(String[] args) {
		
		@SuppressWarnings("deprecation")
		GraphDatabaseService database = new GraphDatabaseFactory().newEmbeddedDatabase("/Users/Leela/Desktop/test.graphdb");
		System.out.println("Database Running!");
		
		try(Transaction tx = database.beginTx()) {
			Node newNode = database.createNode(DatabaseLabels.department);
			newNode.setProperty("name", "Research And Development");
			newNode.setProperty("ref", "RD");
			
			
			ResourceIterator<Node> nodes = database.findNodes(DatabaseLabels.department);
			while(nodes.hasNext()) {
				Node node = nodes.next();
				System.out.println(node.getProperty("name") + " ref " + node.getProperty("ref") );
			}
			
			// Resource Iterator should be closed as soon as
			// its job is finished. even though it will 
			// automatically when a transaction is closed it is better
			// to close manually
			nodes.close();
			tx.success();
		}
				
		System.out.println("Database Shutdown");
		database.shutdown();
		System.out.println("Finished!!!");
	}

}
