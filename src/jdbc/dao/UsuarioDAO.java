package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Usuario;

public class UsuarioDAO {
	private ConnectionFactory connectionFactory;
	
	public UsuarioDAO() {
		this.connectionFactory = new ConnectionFactory();
	}
	
	public Usuario recuperarUsuario(Usuario usuario) {
		try(Connection connection = this.connectionFactory.recuperarConnection()) {
			String query = "SELECT user, password FROM usuario WHERE user = ? AND password = ?";
			
			try(PreparedStatement pstm = connection.prepareStatement(query)) {
				pstm.setString(1, usuario.getUser());
				pstm.setString(2, usuario.getPassword());
				
				ResultSet resultSet = pstm.executeQuery();
				
				Usuario usuarioDao= null;
				if(resultSet.next()) {
					String use = resultSet.getString("user");
					String password = resultSet.getString("password");
					
					usuarioDao = new Usuario(use, password);
					System.out.print(usuarioDao.getUser());
				}
				
				return usuarioDao;
			} 
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
