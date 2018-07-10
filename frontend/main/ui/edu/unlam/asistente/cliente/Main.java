package edu.unlam.asistente.cliente;

import java.util.List;

import edu.unlam.asistente.comunicacion.Cliente;
import edu.unlam.asistente.entidades.Usuario;
import edu.unlam.asistente.ventana.Chat;
import edu.unlam.asistente.ventana.Home;
import edu.unlam.asistente.ventana.Login;

public class Main {
	
	public static Login login;
	public static Home home;
	public static List<Chat> listaChats;
	public static String ip;
	public static int puerto;
	
	public static Cliente cliente;
	
	public static Usuario usuario;

	public static void main(String[] args) {
		
		Main.ip = "127.0.0.1";
		Main.puerto = 12346;
		
		Main.cliente = new Cliente();
		
		Login.cliente = Main.cliente;
		
		Main.login = new Login();
		
		Main.login.setVisible(true);

	}

}
