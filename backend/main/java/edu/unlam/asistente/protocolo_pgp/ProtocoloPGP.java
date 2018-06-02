package edu.unlam.asistente.protocolo_pgp;
import edu.unlam.asistente.asistente_virtual.Bot;
import edu.unlam.asistente.asistente_virtual.IDecision;
import javax.crypto.Cipher;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.regex.Pattern;

public final class ProtocoloPGP implements IDecision {

	private IDecision siguienteDecision;
	
	public static byte[] encrypt(String mensaje, PublicKey key) throws GeneralSecurityException {
    	
    	byte[] message  = new byte [mensaje.length()];
		message = mensaje.getBytes();
    	KeyPair pair = generateKeyPair();
        PrivateKey privateKey = pair.getPrivate();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        byte[] encryptedMessage = cipher.doFinal(message);

        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedPublicKey = cipher.doFinal(pair.getPublic().getEncoded());

        ByteBuffer buffer = ByteBuffer.allocate((encryptedPublicKey.length + encryptedMessage.length) + 4);
        buffer.putInt(encryptedPublicKey.length);
        buffer.put(encryptedPublicKey);
        buffer.put(encryptedMessage);
        return buffer.array();
    }
    

    public static byte[] decrypt(String mensaje, PrivateKey key) throws GeneralSecurityException {
    	byte[] message  = new byte [mensaje.length()];
		message = mensaje.getBytes();
  	
    	ByteBuffer buffer = ByteBuffer.wrap(message);
        int keyLength = buffer.getInt();
        byte[] encyptedPublicKey = new byte[keyLength];
        buffer.get(encyptedPublicKey);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] encodedPublicKey = cipher.doFinal(encyptedPublicKey);

        PublicKey publicKey = getPublicKey(encodedPublicKey);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        byte[] encryptedMessage = new byte[buffer.remaining()];
        buffer.get(encryptedMessage);

        return cipher.doFinal(encryptedMessage);
    }

    protected static PublicKey getPublicKey(byte[] encodedKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(encodedKey);
        return factory.generatePublic(encodedKeySpec);
    }

    protected static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024, SecureRandom.getInstance("SHA1PRNG"));
        return keyPairGenerator.generateKeyPair();
    }
	
	
	@Override
	public String leerMensaje(String mensaje, String usuario) {
		
		if(mensaje.contains("desencripta")) {
			
			//Pattern accion = Pattern.compile("encripta");
			//String ejecutarMetodo = Bot.obtenerString(mensaje, accion); //.obtenerString metodo agregado 2/06 en class Bot
			String[] palabras = mensaje.split(" ");
			String message = palabras[2];
			String key = palabras [3];
			String mensajeDesencriptado = "";
			ProtocoloPGP protocolo = new ProtocoloPGP();
			String respuesta;
			//protocolo.decrypt(message,key);
			
			return respuesta = "@" + usuario + " " + mensajeDesencriptado;
		}
		else if (mensaje.contains("encripta")) {
			String[] palabras = mensaje.split(" ");
			String message = palabras[2];
			String key = palabras [3];
			String mensajeEncriptado = "";
			ProtocoloPGP protocolo = new ProtocoloPGP();
			String respuesta;
			//protocolo.encrypt(message,key);
			
			return respuesta = "@" + usuario + " " + mensajeEncriptado;
		}
						
		return  siguienteDecision.leerMensaje(mensaje, usuario);
	}

	@Override
	public IDecision getSiguienteDecision() {
		return siguienteDecision;
	}

	@Override
	public void setSiguienteDecision(IDecision decision) {
		siguienteDecision = decision;
		
	}

}
