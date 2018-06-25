package edu.unlam.asistente.ventana;

import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.unlam.asistente.comunicacion.Cliente;
import edu.unlam.asistente.database.dao.UsuarioDao;
import edu.unlam.asistente.database.pojo.Usuario;

public class Home extends JFrame {

	private JPanel contentPane;
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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		String[] contactos = obtenerContactos(this.cliente);
		
		JList contactosList = new JList(contactos);
		contactosList.setBounds(308, 77, 116, 173);
		contentPane.add(contactosList);
		
		JLabel lblContactos = new JLabel("Contactos");
		lblContactos.setBounds(308, 44, 116, 22);
		contentPane.add(lblContactos);
		
		
		
	}

	private String[] obtenerContactos(Cliente cliente2) {
		
		String[] listaContactos = new String[2];
		listaContactos[0] = "Contacto pepe";
		listaContactos[1] = "Contacto jose";
		
		try {
			//TODO: cargar nombre de usuario en "user"
			String user = "testUser";
			Usuario usuario = new UsuarioDao().obtenerUsuarioPorLogin(user);
			usuario.getContactos();
			usuario.getEventos();
//			listaContactos = new String[usuario.getContactos().size()-1];
			
//			for (int i = 0 ; i < usuario.getContactos().size() ; i++) {
//				listaContactos[i] = usuario.getContactos().get(i).getUsuario();
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaContactos;
	}
}
