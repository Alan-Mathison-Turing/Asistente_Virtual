package edu.unlam.asistente.imagenes.gif;

import org.junit.Test;

public class GifTest {

	@Test
	public void testGif() {
		Gif gif = new Gif();
		System.out.println(gif.leerMensaje("@Jarvis mostrame un gif sobre miracle", "Jorge"));
	}

}
