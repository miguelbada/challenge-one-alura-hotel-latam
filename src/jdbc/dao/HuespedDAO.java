package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Huesped;

public class HuespedDAO {
	private ConnectionFactory connectionFactory;
	
	public HuespedDAO() {
		this.connectionFactory = new ConnectionFactory();
	}

	public Integer guardar(Huesped huesped) {
		try(Connection connection = this.connectionFactory.recuperarConnection()) {
			String query = "INSERT INTO huesped (nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva) VALUES (?, ?, ?, ?, ?, ?)";
			
			try(PreparedStatement pstm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {	
				pstm.setString(1, huesped.getNombre());
				pstm.setString(2, huesped.getApellido());
				pstm.setDate(3, huesped.getFechaNacimiento());
				pstm.setString(4,huesped.getNacionalidad());
				pstm.setString(5, huesped.getTelefono());
				pstm.setInt(6, huesped.getIdReserva());
				
				pstm.execute();
				
				if (pstm.getUpdateCount() != 1) {
					throw new RuntimeException("No se guardo la reserva ");
				}
				
				try(ResultSet rst = pstm.getGeneratedKeys()) {
					while(rst.next()) {
						huesped.setId(rst.getInt((1))); 
					} 
				}
				
				return huesped.getId();
			
			} 
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Huesped> getHuesped(String apellido) {
		 try(Connection connection = this.connectionFactory.recuperarConnection()) {
	            String sql = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huesped WHERE apellido = ?";

	            try(PreparedStatement statement= connection.prepareStatement(sql)) { //crea un objeto
	                statement.setString(1, apellido);
	                statement.execute(); // Retorna true si es correcto.

	                try(ResultSet resultSet = statement.getResultSet()) {
	                    List<Huesped> huespedes = new ArrayList<>();

	                    while(resultSet.next()) {
	                    	Huesped huesped = new Huesped(
	                                resultSet.getString("nombre"),
	                                resultSet.getString("apellido"),
	                                resultSet.getDate("fecha_nacimiento"),
	                                resultSet.getString("nacionalidad"),
	                                resultSet.getString("telefono"),
	                                resultSet.getInt("id_reserva"));

	                        huesped.setId(resultSet.getInt("id"));

	                        huespedes.add(huesped);
	                    }
	                    //con.close(); // para cerrar después la conección. Lo reemplaza try-with-resources.
	                    return huespedes;
	                }
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	}

	public List<Huesped> listar() {
		try(Connection connection = this.connectionFactory.recuperarConnection()) {
            String sql = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huesped";

            try(PreparedStatement statement= connection.prepareStatement(sql)) { //crea un objeto
                statement.execute(); // Retorna true si es correcto.

                try(ResultSet resultSet = statement.getResultSet()) {
                    List<Huesped> resultado = new ArrayList<>();

                    while(resultSet.next()) {
                        Huesped huesped = new Huesped(
                                resultSet.getString("nombre"),
                                resultSet.getString("apellido"),
                                resultSet.getDate("fecha_nacimiento"),
                                resultSet.getString("nacionalidad"),
                                resultSet.getString("telefono"),
                                resultSet.getInt("id_reserva"));

                        huesped.setId(resultSet.getInt("id"));

                        resultado.add(huesped);
                    }
                    //con.close(); // para cerrar después la conección. Lo reemplaza try-with-resources.
                    return resultado;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	public int update(Integer id, String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono) {
		try(Connection con = this.connectionFactory.recuperarConnection()) {
            String sql = "UPDATE huesped SET" +
                    " nombre = ?" +
                    ", apellido = ?" +
                    ", fecha_nacimiento = ?" +
                    ", nacionalidad = ?" +
                    ", telefono = ?" +
                    " WHERE id = ?";

            try(PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, nombre);
                statement.setString(2, apellido);
                statement.setDate(3, fechaNacimiento);
                statement.setString(4, nacionalidad);
                statement.setString(5, telefono);
                statement.setInt(6, id);
                statement.execute();

                int updateCount = statement.getUpdateCount();
                //con.close(); No es necesario cuando se aplica try-with-resources <<+java 7>>.

                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		
	}

	public int delete(Integer id) {
		try(Connection con = this.connectionFactory.recuperarConnection()) {
            String sql = "DELETE huesped, reserva FROM huesped JOIN reserva ON huesped.id_reserva = reserva.id WHERE huesped.id = ?";
            
            try(PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.execute();

                int getUpdateCount = statement.getUpdateCount();
                //con.close(); Lo reemplaza try-with-resources.

                return getUpdateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }		
	}
	
}
