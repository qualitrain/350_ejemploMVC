package mx.com.qtx.servNegocio;

import mx.com.qtx.dominio.Articulo;

public interface IGestorBD_Articulos {
	Articulo recuperarArticuloXid(String cveArticulo) throws PersistenciaException;

	String getMensajeError(Exception e);

	Articulo insertar(Articulo articulo) throws PersistenciaException;
}
