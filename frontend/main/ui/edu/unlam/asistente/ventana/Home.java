package edu.unlam.asistente.ventana;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;

import edu.unlam.asistente.comunicacion.Cliente;
import edu.unlam.asistente.database.dao.UsuarioDao;
import edu.unlam.asistente.database.pojo.Usuario;

public class Home extends JFrame {

	private static final long serialVersionUID = 9042233624583298311L;
	
	private JPanel contentPane;
	private JList<String> contactosList;
	private JList<String> salasList;
	private JList<String> salasPublicasList;
	private final Cliente cliente;

	/**
	 * Create the frame.
	 */
	public Home(final Cliente cliente) {
		setResizable(false);
		this.cliente = cliente;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		contentPane.setLayout(null);

		//BOTON CERRAR SESION
		JButton btnCerrarSesion = new JButton("Cerrar sesion");
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cerrarSesion();
			}
			
		});
		btnCerrarSesion.setBounds(451, 15, 116, 22);
		contentPane.add(btnCerrarSesion);

		//LABEL BIENVENIDA
		JLabel lblBienvenida = new JLabel("");
		lblBienvenida.setFont(new Font("Segoe Print", Font.BOLD, 20));
		lblBienvenida.setBounds(24, 15, 346, 32);
		lblBienvenida.setText("BIENVENIDO: " + cliente.getNombreUsuario());
		contentPane.add(lblBienvenida);
				
		
		//SECTOR IZQUIERDA - CONTACTOS
		//contactosList = new JList<String>(obtenerContactos(this.cliente));
		DefaultListModel<String> lista = new DefaultListModel<>();
		lista.addElement("pepe");
		lista.addElement("carlos");
		lista.addElement("julia");
		contactosList = new JList<String>(lista);
		
		contactosList.setBounds(163, 14, 47, 115);
		contentPane.add(contactosList);
		
		JLabel lblContactos = new JLabel("Contactos");
		lblContactos.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lblContactos.setBounds(24, 81, 116, 22);
		contentPane.add(lblContactos);
		
		JButton btnAbrirChat = new JButton("Abrir chat");
		btnAbrirChat.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		btnAbrirChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String chatearCon = contactosList.getSelectedValue();
				if(chatearCon == null || chatearCon.isEmpty()){
					JOptionPane.showMessageDialog(null,
							"Operacion incorrecta\n" + "Seleccione un usuario para abrir el chat",
							"Mensaje de informacion", JOptionPane.INFORMATION_MESSAGE);
				}else {
					abrirChatCon(chatearCon);
				}
			}
		});
		btnAbrirChat.setBounds(24, 114, 131, 22);
		contentPane.add(btnAbrirChat);
		
		JScrollPane scrollPaneContactos = new JScrollPane(contactosList);
		scrollPaneContactos.setBounds(24, 167, 131, 203);
		contentPane.add(scrollPaneContactos);
		
		JButton btnEliminarContacto = new JButton("-");
		btnEliminarContacto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String contactoSeleccionado = contactosList.getSelectedValue();
				if(contactoSeleccionado == null){
					JOptionPane.showMessageDialog(null,
							"Operacion incorrecta\n" + "Seleccione un contacto para eliminar",
							"Mensaje de informacion", JOptionPane.INFORMATION_MESSAGE);
				}else {
					if(0 == JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar al contacto: " + contactoSeleccionado,
							"Mensaje de confirmacion", JOptionPane.OK_CANCEL_OPTION)) {
						eliminarContacto(contactoSeleccionado);
					}
				}
			}
		});
		btnEliminarContacto.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnEliminarContacto.setBounds(165, 240, 47, 32);
		contentPane.add(btnEliminarContacto);

		JButton btnAgregarContacto = new JButton("+");
		btnAgregarContacto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String contactoAgregar = JOptionPane.showInputDialog("Ingrese el nombre del contacto a agregar:");
				if(contactoAgregar != null) {
					if(agregarContacto(contactoAgregar)) {
						JOptionPane.showMessageDialog(null, "Se agrego correctamente el usuario: " + contactoAgregar + " a la lista de contactos", 
								"Confirmacion exitosa", JOptionPane.INFORMATION_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null, "Hubo un error al agregar el contacto", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnAgregarContacto.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnAgregarContacto.setBounds(165, 169, 47, 32);
		contentPane.add(btnAgregarContacto);
		
		
		//SECTOR CENTRAL - SALAS PRIVADAS
		JButton btnEntrarSalaPrivada = new JButton("Entrar");
		btnEntrarSalaPrivada.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		btnEntrarSalaPrivada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreSala = salasList.getSelectedValue();
				if(nombreSala != null) {
					ingresarSalaPrivada(nombreSala);
				}else {
					JOptionPane.showMessageDialog(null,
							"Operacion incorrecta\n" + "Seleccione una sala para ingresar",
							"Mensaje de informacion", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnEntrarSalaPrivada.setBounds(239, 114, 74, 22);
		contentPane.add(btnEntrarSalaPrivada);
		
		JLabel lblSalas = new JLabel("Salas privadas");
		lblSalas.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lblSalas.setBounds(239, 81, 116, 22);
		contentPane.add(lblSalas);
		
		JScrollPane listaSalas = new JScrollPane((Component) null);
		listaSalas.setBounds(239, 167, 131, 203);
		contentPane.add(listaSalas);
		
		salasList = new JList<String>(obtenerSalasPrivadas(this.cliente));
		listaSalas.setViewportView(salasList);
		
		
		JButton btnNuevaSalaPrivada = new JButton("+");
		btnNuevaSalaPrivada.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnNuevaSalaPrivada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				JOptionPane.showConfirmDialog(crearNuevaSalaComponent, "Inggrese los datos de la sala nueva", "Creacion de nueva sala", JOptionPane.OK_CANCEL_OPTION);
				crearSala();
			}
		});
		btnNuevaSalaPrivada.setBounds(323, 114, 47, 22);
		contentPane.add(btnNuevaSalaPrivada);
		
		
		//SECTOR DERECHO - SALAS PUBLICAS
		JLabel lblSalasPublicas = new JLabel("Salas publicas");
		lblSalasPublicas.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lblSalasPublicas.setBounds(436, 81, 116, 22);
		contentPane.add(lblSalasPublicas);
		
		JButton btnEntrarSalaPublica = new JButton("Entrar a la sala");
		btnEntrarSalaPublica.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		btnEntrarSalaPublica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String salaPublica = salasPublicasList.getSelectedValue();
				if(salaPublica == null) {
					JOptionPane.showMessageDialog(null,
							"Operacion incorrecta\n" + "Seleccione una sala para ingresar",
							"Mensaje de informacion", JOptionPane.INFORMATION_MESSAGE);
				}else {
					ingresarSalaPublica(salaPublica);
				}
			}
		});
		btnEntrarSalaPublica.setBounds(436, 114, 131, 22);
		contentPane.add(btnEntrarSalaPublica);
		
		JScrollPane scrollPaneSalasPublicas = new JScrollPane((Component) null);
		scrollPaneSalasPublicas.setBounds(436, 167, 131, 203);
		contentPane.add(scrollPaneSalasPublicas);
		
		salasPublicasList = new JList<String>(obtenerSalasPublicas(this.cliente));;
		scrollPaneSalasPublicas.setViewportView(salasPublicasList);
		
		JList list = new JList();
		list.setBounds(117, 403, 121, 40);
		contentPane.add(list);
		
	}

	protected boolean agregarContacto(String contactoAgregar) {
		boolean bolean = false;
		// TODO desarrollar metodo que vaya a back
		return bolean;
	}

	protected void eliminarContacto(String contactoSeleccionado) {
		// TODO desarrollar metodo que vaya a back
		
	}

	protected void ingresarSalaPublica(String salaPublica) {
		// TODO: hacer metodo
		
	}

	protected void ingresarSalaPrivada(String nombreSala) {
		// TODO: hacer metodo
	}

	private ListModel<String> obtenerSalasPublicas(Cliente cliente2) {
		DefaultListModel<String> listaSalasPublicas = new DefaultListModel<>();
		listaSalasPublicas.addElement("Sala Publica1");
		listaSalasPublicas.addElement("Sala Publica2");
		listaSalasPublicas.addElement("Sala Publica3");
		listaSalasPublicas.addElement("Sala Publica4");
		listaSalasPublicas.addElement("Sala Publica5");
		listaSalasPublicas.addElement("Sala Publica6");
		listaSalasPublicas.addElement("Sala Publica7");
		listaSalasPublicas.addElement("Sala Publica8");
		listaSalasPublicas.addElement("Sala Publica9");
		listaSalasPublicas.addElement("Sala Publica10");
		listaSalasPublicas.addElement("Sala Publica11");
		listaSalasPublicas.addElement("Sala Publica12");
		listaSalasPublicas.addElement("Sala Publica13");
		return listaSalasPublicas;
	}

	private void crearSala() {
		// TODO: hacer metodo
	}

	private ListModel<String> obtenerSalasPrivadas(Cliente cliente2) {
		DefaultListModel<String> listaSalas = new DefaultListModel<>();
		
		listaSalas.addElement("Sala1");
		listaSalas.addElement("Sala2");
		listaSalas.addElement("Sala3");
		listaSalas.addElement("Sala4");

		return listaSalas;
	}

	protected void abrirChatCon(String chatearCon) {
		cliente.abrirChatCon(chatearCon);
	}

	private DefaultListModel<String> obtenerContactos(Cliente cliente) {
		
		DefaultListModel<String> listaContactos = new DefaultListModel<>();
		
		try {
			//TODO: CAMBIAR POR LLAMADO VIA SOCKET
			Usuario usuario = new UsuarioDao().obtenerUsuarioPorLogin(this.cliente.getNombreUsuario());
			
			for (int i = 0 ; i < usuario.getContactos().size() ; i++) {
				listaContactos.addElement(usuario.getContactos().get(i).getUsuario());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaContactos;
	}
	
	private boolean cerrarSesion() {
		//TODO: desarrollar metodo
		return true;
	}
}
