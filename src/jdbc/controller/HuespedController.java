package jdbc.controller;

import java.sql.Date;
import java.util.List;

import jdbc.dao.HuespedDAO;
import jdbc.modelo.Huesped;

public class HuespedController {
	private HuespedDAO huespedDAO;
	
	public HuespedController() {
		this.huespedDAO = new HuespedDAO();
	}

	public Integer guardar(Huesped huesped) {
		return this.huespedDAO.guardar(huesped);
	}

	public List<Huesped> listarHuespedes(String apellido) {
		return this.huespedDAO.getHuesped(apellido);
	}

	public int modificarHuesped(Integer id, String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono) {
		
		return this.huespedDAO.update(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono);
	}

	public int eliminarHuesped(Integer id) {
		return this.huespedDAO.delete(id);
	}
	
}
