package edu.unlam.asistente.ventana;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.unlam.asistente.comunicacion.Cliente;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Chat extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldEnviar;
	private JTextArea textAreaChat;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chat frame = new Chat();
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
	public Chat() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 397, 176);
		contentPane.add(scrollPane);
		
		textAreaChat = new JTextArea();
		textAreaChat.setEditable(false);
		scrollPane.setViewportView(textAreaChat);
		textAreaChat.setColumns(10);
		
		textFieldEnviar = new JTextField();
		textFieldEnviar.setBounds(10, 208, 312, 42);
		contentPane.add(textFieldEnviar);
		textFieldEnviar.setColumns(10);
		
		JButton btnEnviar = new JButton("Enviar");
		textFieldEnviar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
		            enviarMensaje();
		        }
			}
		});
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enviarMensaje();
			}
		});
		btnEnviar.setBounds(332, 218, 89, 23);
		contentPane.add(btnEnviar);
	}
	
	public void enviarMensaje() {
		StringBuilder sb = new StringBuilder(textAreaChat.getText());
		String textoEnviar = textFieldEnviar.getText();
		if (!textoEnviar.isEmpty() && textoEnviar != null) {
			sb.append("\n YO: > " + textoEnviar);
			textFieldEnviar.setText(null);
			textAreaChat.setText(sb.toString());
			Cliente.enviarMensaje(textoEnviar);
		}
	}
}
