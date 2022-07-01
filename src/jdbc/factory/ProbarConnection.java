package jdbc.factory;

import java.sql.Connection;
import java.sql.SQLException;

public class ProbarConnection {
	public static void main(String[] args) throws SQLException {
		ConnectionFactory con = new ConnectionFactory();
		Connection connection = con.recuperarConnection();
		
		System.out.println("Cerrando la connection!!");
		
		connection.close();
	}
}
