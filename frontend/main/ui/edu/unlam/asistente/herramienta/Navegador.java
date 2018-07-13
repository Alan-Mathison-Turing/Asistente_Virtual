package edu.unlam.asistente.herramienta;

import java.net.MalformedURLException;
import java.net.URL;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Navegador extends JFXPanel {
	
	private static final long serialVersionUID = 1L;
	private WebEngine webEngine;
	
	
	public Navegador() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				WebView webView = new WebView();
				webEngine = webView.getEngine();
				webView.setPrefSize(400, 250);
				setScene(new Scene(webView));
			}
		});

		setVisible(true);
	}
	
	public void cargarURL(final String url) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String enlace = toURL(url);
				webEngine.load(enlace);
			}
		});
	}
	
	private String toURL(String enlace) {
		try {
			URL url = new URL(enlace);
			return url.toExternalForm();
		} catch(MalformedURLException e) {
			return null;			
		}
	}
	
}
