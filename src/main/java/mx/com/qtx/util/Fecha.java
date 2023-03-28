package mx.com.qtx.util;

import java.util.Date;
import java.util.GregorianCalendar;

public class Fecha {
	
	public static Date crearDate(int aaaa, int mm, int dd) {
		GregorianCalendar gc = new GregorianCalendar(aaaa, mm - 1, dd);
		return gc.getTime();
	}

}