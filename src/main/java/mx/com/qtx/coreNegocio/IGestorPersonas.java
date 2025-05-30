package mx.com.qtx.coreNegocio;

import mx.com.qtx.dominio.GrupoPersonas;

public interface IGestorPersonas {
	GrupoPersonas obtenerPersonas();
	String getMsgError(NegocioException e);
}
