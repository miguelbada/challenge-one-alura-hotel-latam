package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import jdbc.controller.HuespedController;
import jdbc.controller.ReservaController;
import jdbc.modelo.Huesped;
import jdbc.modelo.Reserva;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private HuespedController huespedController;
	private ReservaController reservaController;
	private DefaultTableModel modeloTablaReservas;
	private DefaultTableModel modeloTablaHuespedes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		this.huespedController = new HuespedController();
		this.reservaController = new ReservaController();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 516);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(647, 85, 158, 31);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		JButton btnBuscar = new JButton("");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String busqueda = txtBuscar.getText();
				
				if(tbHuespedes.isVisible()) {
					cargarTablaHuespedes(String.valueOf(busqueda));											
				} else {
					cargarTablaReservas(Integer.valueOf(busqueda));
				}								
			}
		});
		btnBuscar.setBackground(Color.WHITE);
		btnBuscar.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/lupa2.png")));
		btnBuscar.setBounds(815, 75, 54, 41);
		contentPane.add(btnBuscar);
		
		JButton btnEditar = new JButton("");
		btnEditar.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/editar-texto.png")));
		btnEditar.setBackground(SystemColor.menu);
		btnEditar.setBounds(587, 416, 54, 41);
		contentPane.add(btnEditar);
		
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				if(tbHuespedes.isVisible()) {
					modificarHuesped();
				} else {
					modificarReserva();
				}																												
			}
		});
		
		JLabel lblNewLabel_4 = new JLabel("Sistema de Búsqueda");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 24));
		lblNewLabel_4.setBounds(155, 42, 258, 42);
		contentPane.add(lblNewLabel_4);
		
		JButton btnSalir = new JButton("");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
		});
		btnSalir.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/cerrar-sesion 32-px.png")));
		btnSalir.setForeground(Color.WHITE);
		btnSalir.setBackground(Color.WHITE);
		btnSalir.setBounds(815, 416, 54, 41);
		contentPane.add(btnSalir);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBounds(10, 127, 874, 265);
		contentPane.add(panel);
		
		tbHuespedes = new JTable();
		tbHuespedes.setFont(new Font("Arial", Font.PLAIN, 14));
		modeloTablaHuespedes = (DefaultTableModel) tbHuespedes.getModel();
		modeloTablaHuespedes.addColumn("id");
		modeloTablaHuespedes.addColumn("nombre");
		modeloTablaHuespedes.addColumn("apellido");
		modeloTablaHuespedes.addColumn("fecha_nacimiento");
		modeloTablaHuespedes.addColumn("nacionalidad");
		modeloTablaHuespedes.addColumn("telefono");
		modeloTablaHuespedes.addColumn("id_reserva");

		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/persona.png")), tbHuespedes, null);
		
		tbReservas = new JTable();
		tbReservas.setFont(new Font("Arial", Font.PLAIN, 14));
		modeloTablaReservas = (DefaultTableModel) tbReservas.getModel();
		modeloTablaReservas.addColumn("id");
		modeloTablaReservas.addColumn("fecha_entrada");
		modeloTablaReservas.addColumn("fecha_salida");
		modeloTablaReservas.addColumn("valor");
		modeloTablaReservas.addColumn("forma_pago");
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/calendario.png")), tbReservas, null);
		
		JButton btnEliminar = new JButton("");
		btnEliminar.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/deletar.png")));
		btnEliminar.setBackground(SystemColor.menu);
		btnEliminar.setBounds(651, 416, 54, 41);
		contentPane.add(btnEliminar);
		
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {							
				if(tbHuespedes.isVisible()) {
					eliminarHuesped();											
				} else {
					eliminarReserva();
				}								
			}
		});
		
		JButton btnCancelar = new JButton("");
		btnCancelar.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/cancelar.png")));
		btnCancelar.setBackground(SystemColor.menu);
		btnCancelar.setBounds(713, 416, 54, 41);
		contentPane.add(btnCancelar);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(25, 10, 104, 107);
		contentPane.add(lblNewLabel_2);
		setResizable(false);
	}
	
	private void cargarTablaHuespedes(String apellido) { 
		limpiarModeloTabla(modeloTablaHuespedes);		
		  
		List<Huesped> listaHuespedes = this.huespedController.listarHuespedes(apellido);
		  
		listaHuespedes.forEach((huesped) -> {
			modeloTablaHuespedes.addRow( new Object[]{ 
					huesped.getId(),
					huesped.getNombre(), 
					huesped.getApellido(), 
					huesped.getFechaNacimiento(),
					huesped.getNacionalidad(), 
					huesped.getTelefono(), 
					huesped.getIdReserva() 
					});
		}); 
	}
	  
	  private void cargarTablaReservas(Integer id) {
		  limpiarModeloTabla(modeloTablaReservas);	  
		  
		  List<Reserva> listaReservas = this.reservaController.listarReservas(id);
		  System.out.print(listaReservas.size());
		  
		  listaReservas.forEach((reserva) -> {
			  modeloTablaReservas.addRow( new Object[]{ 
					  reserva.getId(),
					  reserva.getFechaEntrada(),
					  reserva.getFechaSalida(),
					  reserva.getValor(),
					  reserva.getFormaPago() 
				      }); 
			  }); 
	  }
	  
	  private void modificarHuesped() {
	        if (!tieneFilaElegida(tbHuespedes)) {
	            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
	            return;
	        }
	        
	        Optional<Object> optional = Optional.ofNullable(modeloTablaHuespedes.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()));
	                if(optional.isPresent()) {	  
	                	Integer id = Integer.valueOf(modeloTablaHuespedes.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());
	                    String nombre = (String) modeloTablaHuespedes.getValueAt(tbHuespedes.getSelectedRow(), 1);
	                    String apellido = (String) modeloTablaHuespedes.getValueAt(tbHuespedes.getSelectedRow(), 2);
	                    Date fechaNacimiento = Date.valueOf(modeloTablaHuespedes.getValueAt(tbHuespedes.getSelectedRow(), 3).toString());
	                    String nacionalidad = (String) modeloTablaHuespedes.getValueAt(tbHuespedes.getSelectedRow(), 4);
	                    String telefono = (String) modeloTablaHuespedes.getValueAt(tbHuespedes.getSelectedRow(), 5);
	                    
	                    int filasModificadas = this.huespedController.modificarHuesped(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono);

	                    if(filasModificadas > 0) {
	                    	JOptionPane.showMessageDialog(this, String.format("%d item modificado con éxito!", filasModificadas));
	                    } else {
	                    	JOptionPane.showMessageDialog(this, String.format("%d item modificado, Se ocasionó un error", filasModificadas));
	                    }
	                    
	                } else {
	                    JOptionPane.showMessageDialog(this, "Por favor, elije un item");
	                }
	  }
	  
	  private void modificarReserva() {
	        if (!tieneFilaElegida(tbReservas)) {
	            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
	            return;
	        }
	        
	        Optional<Object> optional = Optional.ofNullable(modeloTablaReservas.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()));
	                if(optional.isPresent()) {	  
	                	Integer id = Integer.valueOf(modeloTablaReservas.getValueAt(tbReservas.getSelectedRow(), 0).toString());
	                    Date fechaEntrada = Date.valueOf(modeloTablaReservas.getValueAt(tbReservas.getSelectedRow(), 1).toString());
	                    Date fechaSalida = Date.valueOf(modeloTablaReservas.getValueAt(tbReservas.getSelectedRow(), 2).toString());
	                    String valor = (String) (modeloTablaReservas.getValueAt(tbReservas.getSelectedRow(), 3));
	                    String formaPago = (String) modeloTablaReservas.getValueAt(tbReservas.getSelectedRow(), 4);	                  
	                    
	                    int filasModificadas = this.reservaController.modificarReserva(fechaEntrada, fechaSalida, valor, formaPago, id);
	                    
	                    if(filasModificadas > 0) {
	                    	JOptionPane.showMessageDialog(this, String.format("%d item modificado con éxito!", filasModificadas));
	                    } else {
	                    	JOptionPane.showMessageDialog(this, String.format("%d item modificado, Se ocasionó un error", filasModificadas));
	                    }
	                    
	                } else {
	                    JOptionPane.showMessageDialog(this, "Por favor, elije un item");
	                }
	  }
	  
	  private void eliminarHuesped() {
		  if (!tieneFilaElegida(tbHuespedes)) {
	            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
	            return;
	        }
	        
	        Optional<Object> optional = Optional.ofNullable(modeloTablaHuespedes.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()));
	                
	        if(optional.isPresent()) {	  
	                	Integer id = Integer.valueOf(modeloTablaHuespedes.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());	                   
	                    
	                    int filasModificadas = this.huespedController.eliminarHuesped(id);

	                    if(filasModificadas > 0) {
	                    	JOptionPane.showMessageDialog(this, String.format("%d item modificado con éxito!", filasModificadas));
	                    	limpiarModeloTabla(modeloTablaHuespedes);
	                    } else {
	                    	JOptionPane.showMessageDialog(this, String.format("%d item modificado, Se ocasionó un error", filasModificadas));
	                    }
	                    
	                } else {
	                    JOptionPane.showMessageDialog(this, "Por favor, elije un item");
	                }
	  }
	  
	  private void eliminarReserva() {
		  if (!tieneFilaElegida(tbReservas)) {
	            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
	            return;
	        }
	        
	        Optional<Object> optional = Optional.ofNullable(modeloTablaReservas.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()));
	                if(optional.isPresent()) {	  
	                	Integer id = Integer.valueOf(modeloTablaReservas.getValueAt(tbReservas.getSelectedRow(), 0).toString());			                
	                    
	                    int filasModificadas = this.reservaController.eliminarReserva(id);
	                    
	                    if(filasModificadas > 0) {
	                    	JOptionPane.showMessageDialog(this, String.format("%d item modificado con éxito!", filasModificadas));
	                    	limpiarModeloTabla(modeloTablaReservas);
	                    } else {
	                    	JOptionPane.showMessageDialog(this, String.format("%d item modificado, Se ocasionó un error", filasModificadas));
	                    }
	                    
	                } else {
	                    JOptionPane.showMessageDialog(this, "Por favor, elije un item");
	                }	                
			
		}
	  
	  private boolean tieneFilaElegida(JTable tabla) {
	        return tabla.getSelectedRowCount() > 0 || tabla.getSelectedColumnCount() > 0;
	  }
	  
	  private void limpiarModeloTabla(DefaultTableModel tableModel) {
		  tableModel.getDataVector().removeAllElements();
		  tableModel.fireTableDataChanged();
	  }
	  	  
	 
}
