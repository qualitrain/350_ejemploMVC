package mx.com.qtx.util;

public class Validador {
	public static boolean cadenaEnBlaco(String campo){
		if (campo==null)
			return true; 
		if (campo.isEmpty())
			return true;
		for(int i=0;i<campo.length();i++){
			if (campo.charAt(i)!=' ')
				return false;
		}
		return true;
	}
	public static boolean esFlotanteNumerico(String cadNumeroFloat){
		try{
			Float.parseFloat(cadNumeroFloat);
		}
		catch (NumberFormatException e){
			return false;
		}
		return true;
	}

}
