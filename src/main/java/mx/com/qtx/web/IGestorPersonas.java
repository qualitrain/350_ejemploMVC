package mx.com.qtx.web;

import mx.com.qtx.dominio.GrupoPersonas;

public interface IGestorPersonas {
	GrupoPersonas obtenerPersonas();
	String getMsgError(NegocioException e);
}
