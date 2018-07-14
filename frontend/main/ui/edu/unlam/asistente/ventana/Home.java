package edu.unlam.asistente.ventana;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import edu.unlam.asistente.cliente.Main;
import edu.unlam.asistente.comunicacion.Cliente;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Home extends JFrame {

	private static final long serialVersionUID = 9042233624583298311L;
	
	private JPanel contentPane;
	private JList<String> contactosList;
	private JList<String> salasList;
	private JList<String> salasPublicasList;
	
	private DefaultListModel<String> contactosUsuario;
	private DefaultListModel<String> salasPrivadas;
	private DefaultListModel<String> salasPublicas;
	public static Cliente cliente;

	/**
	 * Create the frame.
	 */
	public Home() {
		
		
		this.contactosUsuario = Main.usuario.getContactos();
		this.salasPrivadas = Main.usuario.getSalasPrivadas();
		this.salasPublicas = Main.usuario.getSalasPublicas();
		
		setResizable(false);
		
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
		lblBienvenida.setText("BIENVENIDO: " + Main.usuario.getNombreUsuario());
		contentPane.add(lblBienvenida);
				
		
		//SECTOR IZQUIERDA - CONTACTOS
		contactosList = new JList<String>();
		contactosList.setModel(this.contactosUsuario);
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
					abrirChatIndividual(chatearCon);
				}
			}
		});
		btnAbrirChat.setBounds(24, 114, 131, 22);
		contentPane.add(btnAbrirChat);
		
		JScrollPane scrollPaneContactos = new JScrollPane(contactosList);
		scrollPaneContactos.setBounds(24, 167, 131, 203);
		contentPane.add(scrollPaneContactos);

		JButton btnAgregarContacto = new JButton("+");
		btnAgregarContacto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String contactoAgregar = JOptionPane.showInputDialog("Ingrese el nombre del contacto a agregar:");
				if(contactoAgregar != null) {
					Main.cliente.agregarContacto(contactoAgregar);
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
					abrirChatSala(nombreSala);
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
		
		salasList = new JList<String>(this.salasPrivadas);
		salasList.setModel(this.salasPrivadas);
		listaSalas.setViewportView(salasList);
		
		
		//CREACION DE NUEVA SALA
		JPanel panelCrearSala = new JPanel();
		panelCrearSala.setBounds(100, 200, 600, 500);
		
		JLabel lblNombreSala = new JLabel("Nombre Sala: ");
		lblNombreSala.setBounds(10, 61, 114, 14);
		panelCrearSala.add(lblNombreSala);
		
		JLabel lblTopicoDeLa = new JLabel("Topico de la sala");
		lblTopicoDeLa.setBounds(100, 101, 114, 14);
//		panelCrearSala.add(lblTopicoDeLa);
		
		JTextField txtNombreSala = new JTextField();
		txtNombreSala.setColumns(10);
		txtNombreSala.setBounds(290, 61, 132, 21);
		panelCrearSala.add(txtNombreSala);
		
		JTextField txtTopicoSala = new JTextField();
		txtTopicoSala.setColumns(10);
		txtTopicoSala.setBounds(350, 101, 132, 21);
//		panelCrearSala.add(txtTopicoSala);
		
		//CONTACTOS IZQ SALA
		//JList<String> contactosTotales = new JList<String>();
		//contactosTotales.setModel(this.contactosUsuario);
		//contactosTotales.setBounds(58, 148, 104, 152);
//		panelCrearSala.add(contactosTotales);
		
		//CONTACTOS DER SALA
		JList<String> contactosAgregados = new JList<String>();
		contactosAgregados.setModel(new DefaultListModel<String>());
		contactosAgregados.setBounds(266, 148, 104, 152);
//		panelCrearSala.add(contactosAgregados);
		
		//BOTON PARA AGREGAR CONTACTOS A LA LISTA
//		JButton btnSeleccionar = new JButton(">");
//		btnSeleccionar.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				ListSelectionModel seleccionado = contactosTotales.getSelectionModel();
//				if(seleccionado != null) {
//					//TODO: eliminar de lista izquierda, agregar en lista derecha
////					contactosAgregados.add(seleccionado.)
////					contactosTotales.remove(arg0);(contactosTotales.getSelectedIndex());
//				};
//			}
//		});
//		btnSeleccionar.setBounds(189, 148, 55, 47);
////		panelCrearSala.add(btnSeleccionar);
		
//		JButton btnSacar = new JButton("<");
//		btnSeleccionar.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				ListSelectionModel seleccionado = contactosAgregados.getSelectionModel();
//				if(seleccionado != null) {
//					//TODO: eliminar de lista derecha, agregar en lista izquierda
////					contactosAgregados.add(seleccionado.)
////					contactosTotales.remove(arg0);(contactosTotales.getSelectedIndex());
//				};
//			}
//		});
//		btnSacar.setBounds(189, 217, 55, 47);
//		panelCrearSala.add(btnSacar);
		
		
		//BOTON PARA CREAR NUEVA SALA
		JButton btnNuevaSalaPrivada = new JButton("+");
		btnNuevaSalaPrivada.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnNuevaSalaPrivada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtTopicoSala.setText("");
				int opcionElegida = JOptionPane.showConfirmDialog(null, panelCrearSala, "Creacion de nueva sala", JOptionPane.OK_CANCEL_OPTION);
				if (opcionElegida == JOptionPane.OK_OPTION && txtNombreSala != null) {
					crearSalaPrivada(txtNombreSala.getText());
				}
				
			}
		});
		btnNuevaSalaPrivada.setBounds(323, 114, 47, 22);
		contentPane.add(btnNuevaSalaPrivada);
		
		
		
		//SECTOR DERECHO - SALAS PUBLICAS
		JLabel lblSalasPublicas = new JLabel("Salas publicas");
		lblSalasPublicas.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lblSalasPublicas.setBounds(436, 81, 116, 22);
		contentPane.add(lblSalasPublicas);
		
		JButton btnEntrarSalaPublica = new JButton("Entrar");
		btnEntrarSalaPublica.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		btnEntrarSalaPublica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String salaPublica = salasPublicasList.getSelectedValue();
				if(salaPublica == null) {
					JOptionPane.showMessageDialog(null,
							"Operacion incorrecta\n" + "Seleccione una sala para ingresar",
							"Mensaje de informacion", JOptionPane.INFORMATION_MESSAGE);
				}else {
					abrirChatSala(salaPublica);
				}
			}
		});
		btnEntrarSalaPublica.setBounds(436, 114, 74, 22);
		contentPane.add(btnEntrarSalaPublica);
		
		JScrollPane scrollPaneSalasPublicas = new JScrollPane((Component) null);
		scrollPaneSalasPublicas.setBounds(436, 167, 131, 203);
		contentPane.add(scrollPaneSalasPublicas);
		
		salasPublicasList = new JList<String>();
		salasPublicasList.setModel(this.salasPublicas);
		scrollPaneSalasPublicas.setViewportView(salasPublicasList);
		
		JButton btnNuevaSalaPublica = new JButton("+");
		btnNuevaSalaPublica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtTopicoSala.setText("");
				int opcionElegida = JOptionPane.showConfirmDialog(null, panelCrearSala, "Creacion de nueva sala", JOptionPane.OK_CANCEL_OPTION);
				if (opcionElegida == JOptionPane.OK_OPTION && txtNombreSala != null) {
					crearSalaPublica(txtNombreSala.getText());
				}
			}
		});
		btnNuevaSalaPublica.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNuevaSalaPublica.setBounds(520, 115, 47, 23);
		contentPane.add(btnNuevaSalaPublica);
		
	}

	protected ListModel<JCheckBox> obtenerContactosCheckbox() {
		JList<JCheckBox> checkBoxList = new JList<>();
		for (int i = 0 ; i < this.contactosUsuario.size() ; i++) {
			JCheckBox checkbox = new JCheckBox(this.contactosUsuario.get(i));
			checkBoxList.add(checkbox);
		}
		
		return null;
	}

	protected boolean agregarContacto(String contactoAgregar) {
		boolean bolean = false;
		// TODO desarrollar metodo que vaya a back
		return bolean;
	}

	protected void eliminarContacto(String contactoSeleccionado) {
		// TODO llamar a cliente para solicitar eliminar contacto
		
	}

	protected void abrirChatSala(String nombreSala) {
		for (Chat chatActual : Main.usuario.getVentanasChat()) {
			if (chatActual.getNombre().equals(nombreSala)) {
				chatActual.setVisible(true);
				break;
			}
		}
		
		//TODO: checkear que el usuario este dentro de la sala en este punto
	}
	
	private void abrirChatIndividual(String nombreUsuario2) {
		for (Chat chatActual : Main.usuario.getVentanasChat()) {
			if (chatActual.getNombre().equals(nombreUsuario2)) {
				chatActual.setVisible(true);
				return;
			}
		}
	}
	
	private void crearSalaPublica(String nombreSala) {
		Main.cliente.crearNuevaSala(nombreSala, false, true);
	}

	private void crearSalaPrivada(String nombreSala) {
		Main.cliente.crearNuevaSala(nombreSala, true, true);
	}

	protected void abrirChatCon(String chatearCon) {
		Main.cliente.abrirChatCon(chatearCon);
	}


	private void cerrarSesion() {
		//TODO: desarrollar metodo
		
		//TODO: Sacar esto. Solamente fue realizado para enviar mensajes de prueba
		//Main.cliente.enviarMensaje("sala:" + 1 + "|" +  "Mensaje de prueba");
		Main.cliente.cerrarSocket();
		System.exit(0);
		

	}
	
	public void showDialogUsuarioEncontrado(boolean encontrado) {
		if(encontrado) {
			JOptionPane.showMessageDialog(null, "Se agrego correctamente el contacto", 
					"Confirmacion exitosa", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "No se encontro al contacto", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void showDialogUsuarioYaExisteEnChat() {
		
			JOptionPane.showMessageDialog(null, "El usuario ya existe en la sala", 
					"Confirmacion exitosa", JOptionPane.INFORMATION_MESSAGE);
			}
	public void showDialogRecordatorioAlarma(String nombreEvento, String fecha) {
			JOptionPane.showMessageDialog(null, "Recordatorio de evento " + nombreEvento, 
					"No olvide que el evento " + nombreEvento + " ocurre " + fecha, JOptionPane.WARNING_MESSAGE);
		
	}
	
}
