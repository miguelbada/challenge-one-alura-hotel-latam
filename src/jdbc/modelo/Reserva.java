package jdbc.modelo;

import java.sql.Date;

public class Reserva {
	private Integer id;
	private Date fechaEntrada;
	private Date fechaSalida;
	private String valor;
	private String formaPago;
	
	public Reserva(Date fechaE, Date fechaS, String valor, String formaPago) {
		this.fechaEntrada = fechaE;
		this.fechaSalida = fechaS;
		this.valor = valor;
		this.formaPago = formaPago;
	}
	
	public Date getFechaEntrada() {
		return this.fechaEntrada;
	}
	
	public Date getFechaSalida() {
		return this.getFechaEntrada();
	}
	
	public String getValor() {
		return this.valor;
	}
	
	public String getFormaPago() {
		return this.formaPago;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
	
		return "Reserva {fecha de entrada: " + this.fechaEntrada + ", fecha de salida: " + this.fechaSalida + ", valor: " + this.valor + ", forma de pago: " + this.formaPago;
	}
}
