package jdbc.controller;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import jdbc.dao.ReservaDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Reserva;

public class ReservaController {
	private ReservaDAO reservaDAO;
	
	public ReservaController() {
		this.reservaDAO = new ReservaDAO();
	}
	
	public Integer guardar(Reserva reserva) {
		return this.reservaDAO.guardar(reserva);
	}

	public List<Reserva> listarReservas(Integer id) {
		return this.reservaDAO.getReservas(id);
	}
	
	public int modificarReserva(Date fechaEntrada, Date fechaSalida, String valor, String formaPago, Integer id) {
		return this.reservaDAO.update(fechaEntrada, fechaSalida, valor, formaPago, id);
	}

	public int eliminarReserva(Integer id) {
		return this.reservaDAO.delete(id);
	}
	
}
