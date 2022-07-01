package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;

import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Huesped;
import jdbc.modelo.Reserva;

public class ReservaDAO {
	private ConnectionFactory connectionFactory;
	
	public ReservaDAO() {
		this.connectionFactory = new ConnectionFactory();
	}

	public Integer guardar(Reserva reserva) {
		
		try(Connection connection = this.connectionFactory.recuperarConnection()) {
			String sql = "INSERT INTO reserva (fecha_entrada, fecha_salida, valor, forma_pago) VALUES (?, ?, ?, ?)";
			
			try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				pstm.setDate(1, reserva.getFechaEntrada());
				pstm.setDate(2,reserva.getFechaSalida());
				pstm.setString(3, reserva.getValor());
				pstm.setString(4, reserva.getFormaPago());
				
				pstm.execute();
				
				if (pstm.getUpdateCount() != 1) {
					throw new RuntimeException("No se guardo la reserva ");
				}
				
				try(ResultSet rst = pstm.getGeneratedKeys()) {
					while(rst.next()) {
						reserva.setId(rst.getInt((1))); 
					} 
				}
				
				return reserva.getId();
				 
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Reserva> getReservas(Integer id) {
		
		try(Connection connection = this.connectionFactory.recuperarConnection()) {
            String sql = "SELECT id, fecha_entrada, fecha_salida, valor, forma_pago FROM reserva WHERE id = ?";

            try(PreparedStatement statement= connection.prepareStatement(sql)) { //crea un objeto
                statement.setInt(1, id);
                statement.execute(); // Retorna true si es correcto.

                try(ResultSet resultSet = statement.getResultSet()) {
                    List<Reserva> reservas = new ArrayList<>();

                    while(resultSet.next()) {
                    	Reserva reserva = new Reserva(
                                resultSet.getDate("fecha_entrada"),
                                resultSet.getDate("fecha_salida"),
                                resultSet.getString("valor"),
                                resultSet.getString("forma_pago"));
                                
                        reserva.setId(resultSet.getInt("id"));

                        reservas.add(reserva);
                    }
                    //con.close(); // para cerrar después la conección. Lo reemplaza try-with-resources.
                    return reservas;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	
	public int update(Date fechaEntrada, Date fechaSalida, String valor, String formaPago, Integer id) {
        try(Connection connection = this.connectionFactory.recuperarConnection()) {
            String sql = "UPDATE reserva SET" +
                    " fecha_entrada = ?" +
                    ", fecha_salida = ?" +
                    ", valor = ?" +
                    ", forma_pago = ?" +
                    " WHERE id = ?";

            try(PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setDate(1, fechaEntrada);
                statement.setDate(2, fechaSalida);
                statement.setString(3, valor);
                statement.setString(4, formaPago);
                statement.setInt(5, id);
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
            String sql = "DELETE FROM reserva WHERE id = ?";

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
