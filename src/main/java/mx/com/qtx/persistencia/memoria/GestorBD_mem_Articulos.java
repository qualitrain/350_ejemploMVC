package mx.com.qtx.persistencia.memoria;

import java.util.Hashtable;
import java.util.Map;

import mx.com.qtx.dominio.Articulo;
import mx.com.qtx.servNegocio.IGestorBD_Articulos;
import mx.com.qtx.servNegocio.PersistenciaException;

public class GestorBD_mem_Articulos implements IGestorBD_Articulos {
	private Map<String,Articulo> mapArticulos;

	public GestorBD_mem_Articulos() {
		super();
		this.mapArticulos = new Hashtable<>();
		this.cargarArticulos();
	}

	private void cargarArticulos() {
		Articulo artI = new Articulo("X-5", "Tijera escolar",25.75f,55.30f);
		this.mapArticulos.put(artI.getCveArticulo(), artI);
		artI = new Articulo("C-1", "Calculadora de escritorio", 325.75f,799.90f);
		this.mapArticulos.put(artI.getCveArticulo(), artI);		
		artI = new Articulo("D-1", "Laptop 1T Core i7 Malpine", 13453.75f,26299.90f);
		this.mapArticulos.put(artI.getCveArticulo(), artI);		
		artI = new Articulo("E-1", "Engrapadora de escritorio", 122.0f,223.75f);
		this.mapArticulos.put(artI.getCveArticulo(), artI);		
		artI = new Articulo("Cl-22", "Caja 150 clips", 31.25f,65.10f);
		this.mapArticulos.put(artI.getCveArticulo(), artI);		
	}

	@Override
	public Articulo recuperarArticuloXid(String cveArticulo) {
		return this.mapArticulos.get(cveArticulo);
	}

	@Override
	public String getMensajeError(Exception e) {
		String mensaje = e.getMessage();
		if(e.getCause() != null) {
			mensaje += ", causada por " 
		              + e.getCause().getClass().getName() 
		              + ":" + e.getCause().getMessage();
		}
		return mensaje;
	}

	@Override
	public Articulo insertar(Articulo articulo) throws PersistenciaException {
		if(this.mapArticulos.get(articulo.getCveArticulo()) != null) {
			throw new PersistenciaException("Se intenta duplicar una llave de Articulo "+articulo.getCveArticulo());
		}
		else
			return this.mapArticulos.put(articulo.getCveArticulo(),articulo);
	}

}
