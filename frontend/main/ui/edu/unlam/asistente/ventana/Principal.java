package edu.unlam.asistente.ventana;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JPasswordField;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;

public class Principal extends JFrame {
	private JTextField textField;
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
		
		JLabel txtUsuario = new JLabel();
		txtUsuario.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtUsuario.setLabelFor(this);
		txtUsuario.setVerticalAlignment(SwingConstants.BOTTOM);
		txtUsuario.setToolTipText("");
		txtUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		txtUsuario.setBackground(new Color(255, 255, 255));
		txtUsuario.setText("Usuario");
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setColumns(14);
		
		JButton btnIniciarSesin = new JButton("Iniciar Sesión");
		btnIniciarSesin.setFont(new Font("Tahoma", Font.BOLD, 12));
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(98)
					.addComponent(txtUsuario)
					.addGap(40)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnIniciarSesin)
					.addContainerGap(169, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(120)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(123)
							.addComponent(txtUsuario))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(119)
							.addComponent(btnIniciarSesin)))
					.addContainerGap(121, Short.MAX_VALUE))
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
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		getContentPane().setLayout(groupLayout);
	}
}
