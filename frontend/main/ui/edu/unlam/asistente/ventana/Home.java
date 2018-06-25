package edu.unlam.asistente.ventana;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.unlam.asistente.comunicacion.Cliente;
import edu.unlam.asistente.database.dao.UsuarioDao;
import edu.unlam.asistente.database.pojo.Usuario;

public class Home extends JFrame {

	private static final long serialVersionUID = 9042233624583298311L;
	
	private JPanel contentPane;
	private JList contactosList;
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
		
		contactosList = new JList<String>(contactos);
		contactosList.setBounds(10, 47, 116, 173);
		contentPane.add(contactosList);
		
		JLabel lblContactos = new JLabel("Contactos");
		lblContactos.setBounds(10, 14, 116, 22);
		contentPane.add(lblContactos);
		
		JButton btnAbrirChat = new JButton("Abrir chat");
		btnAbrirChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO: refactorizar el metodo obtener contactos para que devuelva un listModel
//				ListModel<Contacto> listModel = (ListModel<Contacto>) new ArrayList<Contacto>();
//				Contacto contactoSeleccionado = (Contacto) contactosList.getSelectedValue();
				String chatearCon = (String) contactosList.getSelectedValue();
				if(chatearCon == null || !abrirChatCon(chatearCon)){
					//ACCION A TOMAR SI FALLA LA APERTURA DEL CHAT
				}
			}
		});
		btnAbrirChat.setBounds(136, 82, 116, 22);
		contentPane.add(btnAbrirChat);
		
		JLabel lblAbrirChat = new JLabel("Seleccione un contacto para abrir chat");
		lblAbrirChat.setBounds(136, 48, 198, 23);
		contentPane.add(lblAbrirChat);
	}

	protected boolean abrirChatCon(String chatearCon) {
		return this.cliente.abrirChatCon(chatearCon);
	}

	private String[] obtenerContactos(Cliente cliente) {
		
		String[] listaContactos = null;
		
		try {
			Usuario usuario = new UsuarioDao().obtenerUsuarioPorLogin(this.cliente.getNombreUsuario());
			listaContactos = new String[usuario.getContactos().size()];
			
			for (int i = 0 ; i < usuario.getContactos().size() ; i++) {
				listaContactos[i] = usuario.getContactos().get(i).getUsuario();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaContactos;
	}
}
