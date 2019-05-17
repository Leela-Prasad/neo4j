import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class JdbcDriverExample {

	public static void main(String[] args) throws Exception 
	{
		// Using the new JDBC driver
		Class.forName("org.neo4j.jdbc.Driver");

		try(Connection con = DriverManager.getConnection("jdbc:neo4j://localhost:7474/", "neo4j", "secret");
		    Statement stmt = con.createStatement())
		{
			ResultSet rs = stmt.executeQuery("MATCH (n:department) RETURN n.name");
			while(rs.next())
			{
				System.out.println(rs.getString("n.name"));
			}
		}

	}
}