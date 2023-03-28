package mx.com.qtx.servNegocio;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import mx.com.qtx.dominio.Articulo;
import mx.com.qtx.persistencia.GestorBD_mem_Articulos;
import mx.com.qtx.util.Validador;
import mx.com.qtx.web.IGestorArticulos;
import mx.com.qtx.web.NegocioException;

public class GestorArticulos implements IGestorArticulos{
	
	private IGestorBD_Articulos bdArticulos;	
	
	public GestorArticulos() {
		super();
		this.bdArticulos = new GestorBD_mem_Articulos();
	}

	public int validarCamposFormulario(HttpServletRequest reqHttpAltaArticulo,
			HashMap<String,String> tablaErrores,
			Articulo articulo)
	{
		
		int numErrores=0;
		if (this.cveArticuloEsValida(reqHttpAltaArticulo, tablaErrores, articulo, "fmCveArticulo")==false)
			numErrores++;
		if (this.descripcionEsValida(reqHttpAltaArticulo, tablaErrores, articulo, "fmDescripcion")==false)
			numErrores++;
		if (this.costoProv1EsValido(reqHttpAltaArticulo, tablaErrores, articulo, "fmCostoProv1")==false)
			numErrores++;
		if (this.precioListaEsValido(reqHttpAltaArticulo, tablaErrores, articulo, "fmPrecioLista")==false)
			numErrores++;
		return numErrores;
	}

	public boolean cveArticuloEsValida(HttpServletRequest peticionHttpAltaArticulo,
			HashMap<String,String> tablaErrores,
			Articulo articulo,String nombreCampo){
		String cveArticulo = peticionHttpAltaArticulo.getParameter(nombreCampo);
		articulo.setCveArticulo(cveArticulo);
		if (Validador.cadenaEnBlaco(cveArticulo)){
			tablaErrores.put(nombreCampo, "La clave no debe estar vacía");
			return false;
		}
		try{
			Articulo articuloPreExistente=null;
			articuloPreExistente=this.bdArticulos.recuperarArticuloXid(cveArticulo);
			if(articuloPreExistente!=null){
				tablaErrores.put(nombreCampo, "Ya hay un artículo con esa clave en la bd: "+articuloPreExistente);
				return false;
			}
		}
		catch (Exception e){
			if(e instanceof PersistenciaException) {
				tablaErrores.put("BD", this.bdArticulos.getMensajeError(e));
			}
			else {				
				tablaErrores.put(nombreCampo, e.getMessage());
			}
			return false;
		}
		return true;
	}
	public boolean descripcionEsValida(HttpServletRequest reqHttpAltaArticulo,
			HashMap<String,String> tablaErrores,
			Articulo articulo,String nombreCampo){
		String descripcion = reqHttpAltaArticulo.getParameter(nombreCampo);
		articulo.setDescripcion(descripcion);
		if (Validador.cadenaEnBlaco(descripcion)){
			tablaErrores.put(nombreCampo, "La descripción es obligatoria. No puede estar en blanco");
			return false;
		}
		return true;
	}
	public boolean costoProv1EsValido(HttpServletRequest reqHttpAltaArticulo,
			HashMap<String,String> tablaErrores,
			Articulo articulo,String nombreCampo){
		float costoProv1=0.0f;
		articulo.setCostoProv1(0.0f);
		String cadCostoProv1 = reqHttpAltaArticulo.getParameter(nombreCampo);
		if (Validador.cadenaEnBlaco(cadCostoProv1)){
			tablaErrores.put(nombreCampo, "El costo prov 1 es obligatorio");
			return false;
		}
		if(Validador.esFlotanteNumerico(cadCostoProv1)==false){
			tablaErrores.put(nombreCampo, "El costo prov 1 debe ser numérico");
			return false;
		}
		costoProv1=Float.parseFloat(cadCostoProv1);
		articulo.setCostoProv1(costoProv1);
		if (costoProv1<=0){
			tablaErrores.put(nombreCampo, "El costo debe ser mayor que cero");
			return false;
		}
		return true;
	}
	public boolean precioListaEsValido(HttpServletRequest reqHttpAltaArticulo,
			HashMap<String,String> tablaErrores,
			Articulo articulo,String nombreCampo){
		float precioLista=0.0f;
		articulo.setPrecioLista(0.0f);
		String cadPrecioLista = reqHttpAltaArticulo.getParameter(nombreCampo);
		if (Validador.cadenaEnBlaco(cadPrecioLista)){
			tablaErrores.put(nombreCampo, "El precio de lista es obligatorio");
			return false;
		}
		if(Validador.esFlotanteNumerico(cadPrecioLista)==false){
			tablaErrores.put(nombreCampo, "El precio de lista debe ser numérico");
			return false;
		}
		precioLista=Float.parseFloat(cadPrecioLista);
		articulo.setPrecioLista(precioLista);
		if (precioLista<=0){
			tablaErrores.put(nombreCampo, "El precio de lista debe ser mayor que cero");
			return false;
		}
		if (precioLista<=articulo.getCostoProv1()){
			tablaErrores.put(nombreCampo, "El precio de lista debe ser mayor que el costo prov 1");
			return false;
		}
		return true;
	}

	@Override
	public Articulo insertarArticulo(Articulo articulo) {
		try {
			Articulo artPreexistente = this.bdArticulos.recuperarArticuloXid(articulo.getCveArticulo());
			if(artPreexistente !=null) {
				throw new NegocioException("Se intenta insertar un artículo que ya existe: " + artPreexistente);
			}
			else {
				return this.bdArticulos.insertar(articulo);
			}
				
		} catch (PersistenciaException e) {
			throw new NegocioException("Error de persistencia al intentar insertar articulo ", e);
		}
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
