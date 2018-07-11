package edu.unlam.asistente.financiera;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import edu.unlam.asistente.json.JsonReads;

/**
 * Clase que administran el valor de una moneda extranjera con respecto al peso
 * argentino (ARS) actual. <br>
 */
public class Moneda implements Cotizacion {
	/**
	 * Indica cuál es la siguiente cotización que debe intentar resolver la
	 * solicitud. <br>
	 */
	private Cotizacion siguienteCotizacion;

	@Override
	public String leerMoneda(String moneda) {
		Dolar dolar = new Dolar();
		Euro euro = new Euro();
		Real real = new Real();
		Libra libra = new Libra();
		Sol sol = new Sol();
		Boliviano boliviano = new Boliviano();
		Chileno chileno = new Chileno();
		Colombiano colombiano = new Colombiano();
		Guarani guarani = new Guarani();
		Uruguayo uruguayo = new Uruguayo();
		Bolivar bolivar = new Bolivar();
		Mexicano mexicano = new Mexicano();
		Rublo rublo = new Rublo();
		Yen yen = new Yen();
		Yuan yuan = new Yuan();
		Imaginaria imaginaria = new Imaginaria();

		this.setSiguienteCotizacion(dolar);
		dolar.setSiguienteCotizacion(euro);
		euro.setSiguienteCotizacion(real);
		real.setSiguienteCotizacion(libra);
		libra.setSiguienteCotizacion(sol);
		sol.setSiguienteCotizacion(boliviano);
		boliviano.setSiguienteCotizacion(chileno);
		chileno.setSiguienteCotizacion(colombiano);
		colombiano.setSiguienteCotizacion(guarani);
		guarani.setSiguienteCotizacion(uruguayo);
		uruguayo.setSiguienteCotizacion(bolivar);
		bolivar.setSiguienteCotizacion(mexicano);
		mexicano.setSiguienteCotizacion(rublo);
		rublo.setSiguienteCotizacion(yen);
		yen.setSiguienteCotizacion(yuan);
		yuan.setSiguienteCotizacion(imaginaria);

		return this.siguienteCotizacion.leerMoneda(moneda);
	}

	/**
	 * Deuvelve la cotización actual de la moneda. <br>
	 * 
	 * @param moneda
	 *            Nombre de la moneda. <br>
	 * @return Cotización actual de la moneda. <br>
	 */
	public static String obtenerCotizacionActual(String moneda) {
		try {
			JSONObject json = JsonReads
					.readJsonFromUrl(new StringBuilder("http://free.currencyconverterapi.com/api/v5/convert?q=")
							.append(moneda).append("_ARS&compact=ultra").toString());
			return json.getString(moneda + "_ARS");
		} catch (IOException | JSONException e) {
			return "Ha ocurrido un error al obtener la cotización.";
		}
	}

	@Override
	public Cotizacion getSiguienteCotizacion() {
		return this.siguienteCotizacion;
	}

	@Override
	public void setSiguienteCotizacion(Cotizacion cotizacion) {
		this.siguienteCotizacion = cotizacion;
	}
}
