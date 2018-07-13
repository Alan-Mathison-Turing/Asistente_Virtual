package edu.unlam.asistente.ventana;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import edu.unlam.asistente.cliente.Main;

public class Chat extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2400628064788229910L;
	private JPanel contentPane;
	private JTextField textFieldEnviar;
	private JTextPane textAreaChat;
	private HTMLEditorKit htmlEditorKit;
	private HTMLDocument document;
	private int idSala;
	
	public final static String REGEX_MEME = "\\((\\w*)\\)";

	/**
	 * Create the frame.
	 */
	public Chat() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 200, 450, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
//		contentPane.add(textAreaChat);
		textAreaChat.setEditable(false);
		textAreaChat.setEditorKit(htmlEditorKit);
		textAreaChat.setDocument(document);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 397, 386);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(textAreaChat);
	}
	
	public void enviarMensaje() {
		String textoEnviar = textFieldEnviar.getText();
		textFieldEnviar.setText(null);

		if (!textoEnviar.isEmpty() && textoEnviar != null) {
			textFieldEnviar.setText(null);
			try {
				Pattern pattern = Pattern.compile(REGEX_MEME);
				Matcher matcher = pattern.matcher(textoEnviar);
				if(textoEnviar.matches(REGEX_MEME)) {
					matcher.find();
					ImageIcon icon = new ImageIcon("./frontend/img/" + matcher.group(1) + ".jpg");
					
					htmlEditorKit.insertHTML(document, document.getLength(), " > Yo: <br/>", 0, 0, null);
					textAreaChat.setCaretPosition(textAreaChat.getDocument().getLength());
					textAreaChat.insertIcon(icon);
				} else {
					htmlEditorKit.insertHTML(document, document.getLength(), " > Yo: " + textoEnviar, 0, 0, null);
					textAreaChat.setCaretPosition(textAreaChat.getDocument().getLength());
					Main.cliente.enviarMensaje("sala:" + this.idSala + "|" +  textoEnviar); //Al texto a enviar le agrego el id de la sala	
				}
			} catch (BadLocationException | IOException e) {
				System.out.println("INFO: No se pudo interpretar el mensaje enviado por el usuario.");
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
				ImageIcon icon = new ImageIcon(mensaje);
				int ancho = (int) (icon.getIconWidth() * 0.5);
				int largo = (int) (icon.getIconWidth() * 0.5);
				ImageIcon newIcon = new ImageIcon(icon.getImage().getScaledInstance(ancho, largo,  java.awt.Image.SCALE_SMOOTH));
				
				htmlEditorKit.insertHTML(document, document.getLength(), "<br/>", 0, 0, null);
				textAreaChat.setCaretPosition(textAreaChat.getDocument().getLength());
				textAreaChat.insertIcon(newIcon);
			} else {
				htmlEditorKit.insertHTML(document, document.getLength(), " > testBot: " + mensaje, 0, 0, null);	
			}			
			textAreaChat.setCaretPosition(textAreaChat.getDocument().getLength());
		} catch (BadLocationException | IOException e) {
			System.out.println("INFO: No se pudo interpretar el mensaje de respuesta.");
		}
	}
}
