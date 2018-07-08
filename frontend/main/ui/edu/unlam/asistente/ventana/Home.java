package edu.unlam.asistente.ventana;

import java.awt.Color;
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
	private JLabel lblSeleccionarContacto;
	private JLabel lblSeleccionarSala;
	private JLabel lblSeleccioneUnaSala;
	private final Cliente cliente;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Home frame = new Home();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Home(final Cliente cliente) {
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
		contactosList = new JList<String>(obtenerContactos(this.cliente));
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
				lblSeleccionarContacto.setVisible(false);
				if(chatearCon == null){
					lblSeleccionarContacto.setVisible(true);
				}else if (!abrirChatCon(chatearCon)) {
					//FALLO LA APERTURA DEL CHAT
				}
			}
		});
		btnAbrirChat.setBounds(24, 114, 131, 22);
		contentPane.add(btnAbrirChat);
		
		lblSeleccionarContacto = new JLabel("Seleccione un contacto");
		lblSeleccionarContacto.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblSeleccionarContacto.setForeground(Color.RED);
		lblSeleccionarContacto.setBounds(24, 134, 116, 22);
		lblSeleccionarContacto.setVisible(false);
		contentPane.add(lblSeleccionarContacto);
		
		JScrollPane scrollPaneContactos = new JScrollPane(contactosList);
		scrollPaneContactos.setBounds(24, 167, 131, 203);
		contentPane.add(scrollPaneContactos);
		
		
		//SECTOR CENTRAL - SALAS PRIVADAS
		JButton btnEntrarSalaPrivada = new JButton("Entrar");
		btnEntrarSalaPrivada.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		btnEntrarSalaPrivada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreSala = salasList.getSelectedValue();
				if(nombreSala != null) {
					lblSeleccionarSala.setVisible(false);
					ingresarSalaPrivada(nombreSala);
				}else {
					lblSeleccionarSala.setVisible(true);
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
		
		lblSeleccionarSala = new JLabel("Seleccione una sala");
		lblSeleccionarSala.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblSeleccionarSala.setForeground(Color.RED);
		lblSeleccionarSala.setVisible(false);
		lblSeleccionarSala.setBounds(239, 134, 116, 22);
		contentPane.add(lblSeleccionarSala);
		
		
		JButton button = new JButton("+");
		button.setFont(new Font("Tahoma", Font.PLAIN, 8));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearSala();
			}
		});
		button.setBounds(323, 114, 47, 22);
		contentPane.add(button);
		
		
		//SECTOR DERECHO - SALAS PUBLICAS
		JLabel lblSalasPublicas = new JLabel("Salas publicas");
		lblSalasPublicas.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lblSalasPublicas.setBounds(436, 81, 116, 22);
		contentPane.add(lblSalasPublicas);
		
		JButton btnEntrarSalaPublica = new JButton("Entrar a la sala");
		btnEntrarSalaPublica.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		btnEntrarSalaPublica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnEntrarSalaPublica.setBounds(436, 114, 131, 22);
		contentPane.add(btnEntrarSalaPublica);
		
		lblSeleccioneUnaSala = new JLabel("Seleccione una sala");
		lblSeleccioneUnaSala.setForeground(Color.RED);
		lblSeleccioneUnaSala.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblSeleccioneUnaSala.setBounds(436, 138, 116, 22);
		lblSeleccioneUnaSala.setVisible(false);
		contentPane.add(lblSeleccioneUnaSala);
		
		JScrollPane scrollPaneSalasPublicas = new JScrollPane((Component) null);
		scrollPaneSalasPublicas.setBounds(436, 167, 131, 203);
		contentPane.add(scrollPaneSalasPublicas);
		
		salasPublicasList = new JList<String>(obtenerSalasPublicas(this.cliente));;
		scrollPaneSalasPublicas.setViewportView(salasPublicasList);
		
	}

	protected void ingresarSalaPrivada(String nombreSala) {
		
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
		
	}

	private ListModel<String> obtenerSalasPrivadas(Cliente cliente2) {
		DefaultListModel<String> listaSalas = new DefaultListModel<>();
		
		listaSalas.addElement("Sala1");
		listaSalas.addElement("Sala2");
		listaSalas.addElement("Sala3");
		listaSalas.addElement("Sala4");

		return listaSalas;
	}

	protected boolean abrirChatCon(String chatearCon) {
		return this.cliente.abrirChatCon(chatearCon);
	}

	private DefaultListModel<String> obtenerContactos(Cliente cliente) {
		
		DefaultListModel<String> listaContactos = new DefaultListModel<>();
		
		try {
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
		if(true) {
		}
		return true;
	}
}
