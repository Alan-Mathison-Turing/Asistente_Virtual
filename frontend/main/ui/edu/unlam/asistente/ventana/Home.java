package edu.unlam.asistente.ventana;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.unlam.asistente.comunicacion.Cliente;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		String[] contactos = new String[10];
		
		JList contactosList = new JList(contactos);
		contactosList.setBounds(308, 77, 116, 173);
		contentPane.add(contactosList);
		
		JLabel lblContactos = new JLabel("Contactos");
		lblContactos.setBounds(308, 44, 116, 22);
		contentPane.add(lblContactos);
		
		this.cliente = cliente;
	}
}
