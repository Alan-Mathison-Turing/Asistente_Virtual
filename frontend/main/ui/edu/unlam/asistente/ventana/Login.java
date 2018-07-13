package edu.unlam.asistente.ventana;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import edu.unlam.asistente.comunicacion.Cliente;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JFrame {

	private static final long serialVersionUID = -7589924510729861612L;
	private JPanel contentPane;
	private JPasswordField pwdSecreto;
	private JTextField txtUsuario_1;
	private JTextField tfServidor;
	private JTextField tfPuertoServidor;
	public static Cliente cliente;

	/**
	 * Create the frame.
	 */
	public Login() {
		
		setResizable(false);
		 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		

	
		JLabel lblUsuario = new JLabel("Usuario");
		
		contentPane.add(lblUsuario);

		JLabel lblContrasea = new JLabel("Contraseña");
		lblContrasea.setVerticalAlignment(SwingConstants.TOP);
		lblContrasea.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(lblContrasea);

		pwdSecreto = new JPasswordField();
		contentPane.add(pwdSecreto);
		pwdSecreto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char tecla=e.getKeyChar();
				if(tecla==KeyEvent.VK_ENTER){
					iniciarSesion();
				}
			}
		});
		
		txtUsuario_1 = new JTextField();
		contentPane.add(txtUsuario_1);
		txtUsuario_1.setColumns(10);
		txtUsuario_1.grabFocus();

		txtUsuario_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				char tecla=arg0.getKeyChar();
				if(tecla==KeyEvent.VK_ENTER){
					pwdSecreto.grabFocus();
				}
				
			}
		});

		JButton btnIniciarSesion = new JButton("Iniciar Sesion");
		btnIniciarSesion.setVerticalAlignment(SwingConstants.TOP);
		
		btnIniciarSesion.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				char tecla=e.getKeyChar();
				if(tecla==KeyEvent.VK_ENTER) {

					iniciarSesion();
			}
			}
		});
		
		
		btnIniciarSesion.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

		       iniciarSesion();
				

			}
		}
		);
		contentPane.add(btnIniciarSesion);

		JLabel lblRegistrarse = new JLabel("Registrarse");
		lblRegistrarse.setLocation(170, 205);
		lblRegistrarse.setSize(70, 14);
		lblRegistrarse.setVerticalAlignment(SwingConstants.BOTTOM);
		lblRegistrarse.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistrarse.setForeground(new Color(0, 0, 255));
		contentPane.add(lblRegistrarse);

		JLabel lblDatosDeConexin = new JLabel("Datos de Conexi\u00F3n:");
		lblDatosDeConexin.setFont(new Font("Tahoma", Font.BOLD, 11));

		JLabel lblIpServidor = new JLabel("IP Servidor:");

		JLabel lblPuertoServidor = new JLabel("Puerto Servidor:");

		tfServidor = new JTextField();
		tfServidor.setEnabled(false);
		tfServidor.setText("localhost");
		tfServidor.setColumns(10);
		tfServidor.setNextFocusableComponent(lblUsuario);
		tfPuertoServidor = new JTextField();
		tfPuertoServidor.setEnabled(false);
		tfPuertoServidor.setText("12346");
		tfPuertoServidor.setColumns(10);
		tfPuertoServidor.transferFocus();

		JLabel lblDatosDeUsuario = new JLabel("Datos de usuario:");
		lblDatosDeUsuario.setFont(new Font("Tahoma", Font.BOLD, 11));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(34)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDatosDeUsuario)
						.addComponent(lblDatosDeConexin))
					.addContainerGap(292, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(96)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblUsuario, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblIpServidor)
						.addComponent(lblPuertoServidor)
						.addComponent(lblContrasea, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(tfPuertoServidor, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
						.addComponent(tfServidor, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
						.addComponent(txtUsuario_1, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
						.addComponent(pwdSecreto, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
					.addGap(94))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(143, Short.MAX_VALUE)
					.addComponent(btnIniciarSesion, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
					.addGap(164))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(176)
					.addComponent(lblRegistrarse, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(188, Short.MAX_VALUE))
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
								.addComponent(tfServidor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(9)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPuertoServidor)
								.addComponent(tfPuertoServidor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
										.addComponent(lblContrasea)))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(70)
							.addComponent(lblDatosDeUsuario)))
					.addGap(13)
					.addComponent(btnIniciarSesion)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblRegistrarse)
					.addGap(48))
		);
		contentPane.setLayout(gl_contentPane);
	}
	public void iniciarSesion(){
		String ip = tfServidor.getText();
		int puerto = Integer.parseInt(tfPuertoServidor.getText());
		String usuario = txtUsuario_1.getText();
		char[] passwd = pwdSecreto.getPassword();
		String clave = new String(passwd);
		
		if(ip.length() > 0) {
			
			try {
				
				Login.cliente.createSocket(ip, puerto);
				
				if(usuario.length() > 0 && clave.length() > 0) {
					
					Login.cliente.solicitarAutenticacion(usuario, clave);
					
				} else {

					JOptionPane.showMessageDialog(null,
							"Datos incompletos\n" + " Ingrese su usuario y contraseña",
							"Mensaje de Error", JOptionPane.INFORMATION_MESSAGE);
					txtUsuario_1.setText(null);
					pwdSecreto.setText(null);
					txtUsuario_1.grabFocus();

					
				}
				
			} catch (IOException e) {
				
				JOptionPane.showMessageDialog(null,
						"Acceso Denegado\n" + " Puerto o IP elegidos incorrectos",
						"Mensaje de Error", JOptionPane.INFORMATION_MESSAGE);
				
				
				e.printStackTrace();
			}
			
		} else {
			JOptionPane.showMessageDialog(null,
					"Datos incompletos\n" + " Por favor ingrese una direccion IP",
					"Mensaje de Error", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	public void loginIncorrecto() {
		JOptionPane.showMessageDialog(null,
				"Acceso Denegado\n" + " Usuario o contraseña ingresados no son correctos",
				"Mensaje de Error", JOptionPane.INFORMATION_MESSAGE);
		txtUsuario_1.setText(null);
		pwdSecreto.setText(null);
		txtUsuario_1.grabFocus();
	}
	
}
