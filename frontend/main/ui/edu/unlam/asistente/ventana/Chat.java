package edu.unlam.asistente.ventana;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import edu.unlam.asistente.comunicacion.Cliente;

public class Chat extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2400628064788229910L;
	private JPanel contentPane;
	private JTextField textFieldEnviar;
	private final Cliente cliente;
	private JTextPane textAreaChat;
	JLabel imageLabel = new JLabel();
	HTMLEditorKit htmlEditorKit;
	HTMLDocument document;
	Graphics graphic;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Chat frame = new Chat();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
		
//		SwingUtilities.invokeLater(new Runnable() {
//			
//			@Override
//			public void run() {
//				while(true) {
//					Mensaje msj = Cliente.colaMensajes.poll();
//					if (textAreaChat != null && msj != null) {
//						textAreaChat.append("\n > BOT: " + msj.getMensaje());
//					}
//				}
//				
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Chat(final Cliente cliente) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 450, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.cliente = cliente; 
		
		htmlEditorKit = new HTMLEditorKit();
		document = new HTMLDocument();
		
		textFieldEnviar = new JTextField();
		textFieldEnviar.setBounds(10, 408, 312, 42);
		textFieldEnviar.setColumns(10);
		contentPane.add(textFieldEnviar);
			
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
		btnEnviar.setBounds(335, 418, 89, 23);
		contentPane.add(btnEnviar);
		textAreaChat = new JTextPane();
		textAreaChat.setBounds(10, 11, 395, 386);
		contentPane.add(textAreaChat);
		textAreaChat.setEditable(false);
		textAreaChat.setEditorKit(htmlEditorKit);
		textAreaChat.setDocument(document);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 397, 386);
		
		contentPane.add(scrollPane);
	}
	
	public void enviarMensaje() {
		String textoEnviar = textFieldEnviar.getText();
		textFieldEnviar.setText(null);

		if (!textoEnviar.isEmpty() && textoEnviar != null) {
			textFieldEnviar.setText(null);
			try {
				htmlEditorKit.insertHTML(document, document.getLength(), " > Yo: " + textoEnviar, 0, 0, null);
				textAreaChat.setCaretPosition(textAreaChat.getDocument().getLength());
				cliente.enviarMensaje(textoEnviar);
			} catch (BadLocationException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void actualizarChat(String mensaje) {
		try {
			if(mensaje.endsWith(".gif")) {
				URL url = new URL(mensaje);
				ImageIcon icon = new ImageIcon(url);
				htmlEditorKit.insertHTML(document, document.getLength(), "<br/>", 0, 0, null);
				textAreaChat.setCaretPosition(textAreaChat.getDocument().getLength());
				textAreaChat.insertIcon(icon);
				
			} else if(mensaje.endsWith(".jpg")) {
				String text = "<img src=\"" + mensaje + "\" height=\"50\" width=\"50\"></img>"; 
				htmlEditorKit.insertHTML(document, document.getLength(), " > testBot: " + text, 0, 0, null);
			} else {
				htmlEditorKit.insertHTML(document, document.getLength(), " > testBot: " + mensaje, 0, 0, null);	
			}			
			textAreaChat.setCaretPosition(textAreaChat.getDocument().getLength());
		} catch (BadLocationException | IOException e) {
			e.printStackTrace();
		}
	}
}
