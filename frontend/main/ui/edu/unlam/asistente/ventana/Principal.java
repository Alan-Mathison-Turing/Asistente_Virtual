package edu.unlam.asistente.ventana;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Principal extends JFrame {

	private static final long serialVersionUID = -7589924510729861612L;
	private JPanel contentPane;
	private JPasswordField pwdSecreto;
	private JTextField txtUsuario_1;
	private JLabel lblErrorLogin;
	private JTextField textFieldIpServidor;
	private JTextField textFieldPuertoServidor;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
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
	public Principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblUsuario = new JLabel("Usuario");
		contentPane.add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contraseña");
		contentPane.add(lblContrasea);
		
		pwdSecreto = new JPasswordField();
		pwdSecreto.setText("secreto");
		contentPane.add(pwdSecreto);
		
		txtUsuario_1 = new JTextField();
		txtUsuario_1.setText("Usuario");
		contentPane.add(txtUsuario_1);
		txtUsuario_1.setColumns(10);
		
		JButton btnIniciarSesion = new JButton("Iniciar Sesion");
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(autenticarUsuario()) {
					
				} else {
					lblErrorLogin.setVisible(true);
				}
			}
		});
		contentPane.add(btnIniciarSesion);
		
		JLabel lblRegistrarse = new JLabel("Registrarse");
		lblRegistrarse.setForeground(new Color(0, 0, 255));
		contentPane.add(lblRegistrarse);
		
		JLabel lblseOlvidoSu = new JLabel("¿Se olvido su contraseña?");
		lblseOlvidoSu.setForeground(new Color(0, 0, 255));
		contentPane.add(lblseOlvidoSu);
		
		lblErrorLogin = new JLabel("El usuario y/o la contrasa\u00F1a son inv\u00E1lidos");
		lblErrorLogin.setVisible(false);
		lblErrorLogin.setForeground(Color.RED);
		
		JLabel lblDatosDeConexin = new JLabel("Datos de Conexi\u00F3n:");
		
		JLabel lblIpServidor = new JLabel("IP Servidor:");
		
		JLabel lblPuertoServidor = new JLabel("Puerto Servidor:");
		
		textFieldIpServidor = new JTextField();
		textFieldIpServidor.setColumns(10);
		
		textFieldPuertoServidor = new JTextField();
		textFieldPuertoServidor.setColumns(10);
		
		JLabel lblDatosDeUsuario = new JLabel("Datos de usuario:");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(149)
							.addComponent(btnIniciarSesion, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(139)
							.addComponent(lblseOlvidoSu, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(169)
							.addComponent(lblRegistrarse, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(96)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblUsuario, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblIpServidor)
								.addComponent(lblPuertoServidor)
								.addComponent(lblContrasea, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
							.addGap(10)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(textFieldPuertoServidor, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(textFieldIpServidor, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(txtUsuario_1, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(pwdSecreto, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))))
					.addGap(94))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(93)
					.addComponent(lblErrorLogin)
					.addContainerGap(134, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(34)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDatosDeUsuario)
						.addComponent(lblDatosDeConexin))
					.addContainerGap(295, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDatosDeConexin)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblIpServidor)
								.addComponent(textFieldIpServidor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(9)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPuertoServidor)
								.addComponent(textFieldPuertoServidor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(6)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblUsuario)
										.addComponent(txtUsuario_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(35)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(pwdSecreto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblContrasea))))
							.addGap(21)
							.addComponent(lblErrorLogin)
							.addGap(18)
							.addComponent(btnIniciarSesion)
							.addGap(2)
							.addComponent(lblseOlvidoSu)
							.addGap(11)
							.addComponent(lblRegistrarse))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(70)
							.addComponent(lblDatosDeUsuario))))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	private boolean autenticarUsuario() {
		
		
		return false;
	}
}
