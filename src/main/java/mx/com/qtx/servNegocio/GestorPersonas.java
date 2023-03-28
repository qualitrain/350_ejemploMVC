package mx.com.qtx.servNegocio;

import mx.com.qtx.dominio.GrupoPersonas;
import mx.com.qtx.persistencia.GestorBD_mem_Personas;
import mx.com.qtx.web.IGestorPersonas;
import mx.com.qtx.web.NegocioException;

public class GestorPersonas implements IGestorPersonas{
	private IGestorBD_Personas bdPersonas;
	
	public GestorPersonas() {
		this.bdPersonas = new GestorBD_mem_Personas();
	}
	public GrupoPersonas obtenerPersonas() {
		return this.bdPersonas.recuperarPersonasTodas();		
	}
	@Override
	public String getMsgError(NegocioException e) {
		String mensaje = e.getMessage();
		if(e.getCause() != null) {
			mensaje += ", causada por " 
		              + e.getCause().getClass().getName() 
		              + ":" + e.getCause().getMessage();
		}
		return mensaje;
	};
}
