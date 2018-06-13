package edu.unlam.asistente.ventana;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class Login extends JFrame {

	private static final long serialVersionUID = 314481367329554705L;
	private JPanel contentPane;
	private JTextField textFieldUser;
	private JTextField textFieldPassword;
	private JTextField textFieldOutput;
	private JLabel lblDatosInvalidos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLogin = new JButton("Ingresar");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int codigoRetorno = login(textFieldUser.getText(), textFieldPassword.getText());
				lblDatosInvalidos.setVisible(false);
				if(codigoRetorno == 0) {
					textFieldOutput.setText("Login correcto");
				}else {
					textFieldOutput.setText("Login incorrecto");
					lblDatosInvalidos.setVisible(true);
					textFieldUser.setText("");
					textFieldPassword.setText("");
				}
			}

		});
		btnLogin.setBounds(10, 194, 123, 35);
		contentPane.add(btnLogin);
		
		textFieldUser = new JTextField();
		textFieldUser.setBounds(10, 74, 86, 20);
		contentPane.add(textFieldUser);
		textFieldUser.setColumns(10);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setForeground(Color.BLACK);
		lblUsuario.setBounds(10, 48, 86, 20);
		contentPane.add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contraseña:");
		lblContrasea.setBounds(10, 120, 86, 20);
		contentPane.add(lblContrasea);
		
		textFieldPassword = new JTextField();
		textFieldPassword.setBounds(10, 148, 86, 20);
		contentPane.add(textFieldPassword);
		textFieldPassword.setColumns(10);
		
		textFieldOutput = new JTextField();
		textFieldOutput.setBounds(133, 74, 291, 63);
		contentPane.add(textFieldOutput);
		textFieldOutput.setColumns(10);
		
		lblDatosInvalidos = new JLabel("Ingreso incorrecto");
		lblDatosInvalidos.setEnabled(true);
		lblDatosInvalidos.setVisible(false);
		lblDatosInvalidos.setForeground(Color.RED);
		lblDatosInvalidos.setBounds(10, 230, 123, 20);
		contentPane.add(lblDatosInvalidos);
	}
	
	private int login(String user, String password) {
		int retorno = Double.compare(Math.random(), Math.random()) + 1;
		return retorno;
	}
}
