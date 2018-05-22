package calendario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Calendario {
	
	private final static SimpleDateFormat SQLITE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static Date getDateFromString(String fecha) throws ParseException {
		
		return SQLITE_FORMATTER.parse(fecha);
	}
}
