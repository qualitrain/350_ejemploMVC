package mx.com.qtx.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import mx.com.qtx.dominio.Articulo;

public interface IGestorArticulos {
	int validarCamposFormulario(HttpServletRequest reqHttpAltaArticulo,
			HashMap<String,String> tablaErrores, Articulo articulo);
	Articulo insertarArticulo(Articulo articulo);
	String getMsgError(NegocioException e);
}
