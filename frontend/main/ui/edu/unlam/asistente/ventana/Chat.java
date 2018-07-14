package edu.unlam.asistente.ventana;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import edu.unlam.asistente.cliente.Main;
import edu.unlam.asistente.entidades.MensajeChat;
import edu.unlam.asistente.herramienta.Navegador;

public class Chat extends JFrame {

	private Chat self = this;
	
	private static final long serialVersionUID = 2400628064788229910L;
	private JPanel contentPane;
	private JTextField textFieldEnviar;
	private JTextPane textAreaChat;
	private HTMLEditorKit htmlEditorKit;
	private HTMLDocument document;

	private int idSala;
	//private ArrayList<MensajeChat> mensajes;
	private String nombre;
	private int dueñoId;
	private boolean esPrivado;
	private boolean esGrupal;
	
	public final static String REGEX_MEME = "@\\w*(?:\\w|\\s|\\,)* \\((\\w*)\\)";
	
	public boolean esPrivado() {
		return this.esPrivado;
	}
	
	public boolean esGrupal() {
		return this.esGrupal;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getidSala() {
		return this.idSala;
	}
	

	/**
	 * Create the frame.
	 */
	public Chat(int idSala, String nombre, int dueñoId, int esPrivado, int esGrupal) {
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char tecla=e.getKeyChar();
				if(tecla == KeyEvent.VK_ESCAPE) {
					cerrarChat();
				}
				if(tecla!=KeyEvent.VK_ENTER && tecla != KeyEvent.VK_ESCAPE) {
					textFieldEnviar.grabFocus();
				}
			}
		});
		addWindowFocusListener(new WindowFocusListener(){

			@Override
			public void windowGainedFocus(WindowEvent arg0) {
				textFieldEnviar.grabFocus();
				
			}

			@Override
			public void windowLostFocus(WindowEvent arg0) {
				textFieldEnviar.grabFocus();
				
			}
			
		});
	
		
		//this.mensajes = new ArrayList<MensajeChat>();
		this.idSala = idSala;
		this.nombre = nombre;
		this.dueñoId = dueñoId;
		this.esPrivado = esPrivado == 1;
		this.esGrupal = esGrupal == 1;
		
		this.setTitle(this.nombre + (this.esPrivado? " (privado)":" (público)"));
		
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
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					cerrarChat();
				}
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
		textAreaChat.setEditable(false);
		textAreaChat.setEditorKit(htmlEditorKit);
		textAreaChat.setDocument(document);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 38, 414, 359);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(textAreaChat);
		
		textAreaChat.setCaretPosition(textAreaChat.getDocument().getLength());
		
		JButton btnAgegarContacto = new JButton("Agregar contacto");
		btnAgegarContacto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String contactoAgregar = JOptionPane.showInputDialog("Ingrese el nombre del contacto para invitar:");
				if(contactoAgregar != null) {
					Main.cliente.agregarContactoASala(self.idSala,contactoAgregar);
				}
			}
		});
		
		btnAgegarContacto.setVerticalAlignment(SwingConstants.BOTTOM);
		btnAgegarContacto.setBounds(262, 4, 147, 23);
		contentPane.add(btnAgegarContacto);
		
		btnAgegarContacto.setVisible(false);
		if (this.esGrupal) {
			if (this.esPrivado) {
				if (this.dueñoId == Main.usuario.getID()) {
					btnAgegarContacto.setVisible(true);
				}
			}
		}
	}
	public void cerrarChat() {
		this.setVisible(false);
	}
	
	public void enviarMensaje() {
		String textoEnviar = textFieldEnviar.getText();
		textFieldEnviar.setText(null);

		if (!textoEnviar.isEmpty() && textoEnviar != null) {
			textFieldEnviar.setText(null);
			try {
				if(textoEnviar.matches(REGEX_MEME)){
					Main.cliente.enviarMensaje(this.idSala, textoEnviar);
				}else{
					htmlEditorKit.insertHTML(document, document.getLength(), " > Yo: " + textoEnviar, 0, 0, null);
					textAreaChat.setCaretPosition(textAreaChat.getDocument().getLength());
					Main.cliente.enviarMensaje(this.idSala, textoEnviar);
				}
				
				
			} catch (BadLocationException | IOException e) {
				System.out.println("INFO: No se pudo interpretar el mensaje enviado por el usuario.");
			}
		}
	}
	
	public void actualizarChat(MensajeChat mensaje) {
		try {
			String txtMensaje = mensaje.getMensaje(); 
			if(txtMensaje.endsWith(".gif")) {
				URL url = new URL(txtMensaje);
				ImageIcon icon = new ImageIcon(url);
				htmlEditorKit.insertHTML(document, document.getLength(), "<br/>", 0, 0, null);
				textAreaChat.setCaretPosition(textAreaChat.getDocument().getLength());
				textAreaChat.insertIcon(icon);
				
			} else if(txtMensaje.endsWith(".jpg")) {
				ImageIcon icon = null;

				if (mensaje.getMensaje().contains("gag")) {
					URL url = new URL(txtMensaje);
					icon = new ImageIcon(url);
				} else {
					htmlEditorKit.insertHTML(document, document.getLength(), " > Yo: ", 0, 0, null);
					icon = new ImageIcon(mensaje.getMensaje());
				}
				Image image = icon.getImage();
				Image newimg = image.getScaledInstance(256, 256,  java.awt.Image.SCALE_DEFAULT);

				icon = new ImageIcon(newimg);
				
				htmlEditorKit.insertHTML(document, document.getLength(), "", 0, 0, null);
				
				textAreaChat.setCaretPosition(textAreaChat.getDocument().getLength());
				textAreaChat.insertIcon(icon);
				textAreaChat.setCaretPosition(textAreaChat.getDocument().getLength());
				
			} else if(txtMensaje.contains("youtube")) {
				JPanel videoPanel = new JPanel();
				videoPanel.setSize(400,250);
				Navegador browser = new Navegador();
				browser.cargarURL(txtMensaje);
				videoPanel.add(browser);
				textAreaChat.setCaretPosition(textAreaChat.getDocument().getLength());
				textAreaChat.insertComponent(videoPanel);
			} else {
				//htmlEditorKit.insertHTML(document, document.getLength(), " > testBot: " + mensaje, 0, 0, null);	
				htmlEditorKit.insertHTML(document, document.getLength(), " >> "+ mensaje.getUsuario() +": " + txtMensaje, 0, 0, null);	
			}			
			textAreaChat.setCaretPosition(textAreaChat.getDocument().getLength());
			
		} catch (BadLocationException | IOException e) {
			System.out.println("INFO: No se pudo interpretar el mensaje de respuesta.");
		}
	}
}
