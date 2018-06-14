package edu.unlam.asistente.ventana;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class Principal extends JFrame {

	private static final long serialVersionUID = -7589924510729861612L;
	private JPasswordField pwdSecreto;
	private JTextField txtUsuario_1;

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
		
		//TODO: CHECKEAR LA LINEA SIGUIENTE, ESTABA AL FINAL DE LA CLASE,
		//ESTABA JUSTO ANTES DE ESTA LINEA: getContentPane().setLayout(groupLayout);
		//LA MOVI PARA QUE COMPILE
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGap(0, 434, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGap(0, 261, Short.MAX_VALUE)
		);
		getContentPane().setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(65, 68, 46, 14);
		getContentPane().add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contraseña");
		lblContrasea.setBounds(65, 123, 64, 14);
		getContentPane().add(lblContrasea);
		
		pwdSecreto = new JPasswordField();
		pwdSecreto.setText("secreto");
		pwdSecreto.setBounds(139, 120, 137, 20);
		getContentPane().add(pwdSecreto);
		
		txtUsuario_1 = new JTextField();
		txtUsuario_1.setText("Usuario");
		txtUsuario_1.setBounds(139, 65, 137, 20);
		getContentPane().add(txtUsuario_1);
		txtUsuario_1.setColumns(10);
		
		JButton btnIniciarSesion = new JButton("Iniciar Sesion");
		btnIniciarSesion.setBounds(149, 187, 108, 23);
		getContentPane().add(btnIniciarSesion);
		
		JLabel lblRegistrarse = new JLabel("Registrarse");
		lblRegistrarse.setForeground(new Color(0, 0, 255));
		lblRegistrarse.setBounds(169, 237, 62, 14);
		getContentPane().add(lblRegistrarse);
		
		JLabel lblseOlvidoSu = new JLabel("¿Se olvido su contraseña?");
		lblseOlvidoSu.setForeground(new Color(0, 0, 255));
		lblseOlvidoSu.setBounds(139, 212, 137, 14);
		getContentPane().add(lblseOlvidoSu);
		getContentPane().setLayout(groupLayout);
	}
}
