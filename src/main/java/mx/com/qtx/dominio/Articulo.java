package mx.com.qtx.dominio;

import java.sql.SQLException;
import java.util.HashMap;

import mx.com.qtx.persistencia.GestorBD;

public class Articulo {
	private String cveArticulo;
	private String descripcion;
	private float costoProv1;
	private float precioLista;

	public Articulo(String cveArticulo, String descripcion, float costoProv1,
			float precioLista) {
		super();
		this.cveArticulo = cveArticulo;
		this.descripcion = descripcion;
		this.costoProv1 = costoProv1;
		this.precioLista = precioLista;
	}

	public Articulo(String cveArticulo) {
		super();
		this.cveArticulo = cveArticulo;
	}

	public static HashMap<String,Articulo> recuperarTodos() throws SQLException{
		return GestorBD.recuperarArticulosTodos();
	}
	public static Articulo recuperaXid(String cveArticulo)throws SQLException{
		return GestorBD.recuperarArticuloXid(cveArticulo);
	}
	public static void factorizarPrecios (float factor) throws SQLException{
		GestorBD.factorizarPrecioArticulos(factor);
	}
	public static int getCantidadArticulosDadosDeAlta()throws SQLException{
		return GestorBD.recuperarCuantosArticulosHayEnBD();
	}
	public String getCveArticulo() {
		return cveArticulo;
	}

	public void setCveArticulo(String cveArticulo) {
		this.cveArticulo = cveArticulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getCostoProv1() {
		return costoProv1;
	}

	public void setCostoProv1(float costoProv1) {
		this.costoProv1 = costoProv1;
	}

	public float getPrecioLista() {
		return precioLista;
	}

	public void setPrecioLista(float precioLista) {
		this.precioLista = precioLista;
	}

	@Override
	public String toString() {
		return "Articulo [cveArticulo=" + cveArticulo + ", descripcion="
				+ descripcion + ", costoProv1=" + costoProv1 + ", precioLista="
				+ precioLista + "]";
	}
	public int insertarEnBD()throws SQLException{
		return GestorBD.insertarArticulo(this);
		
	}
	public int actualizarEnBD()throws SQLException{
		return GestorBD.actualizarArticulo(this);
	}
	public int eliminarEnBD()throws SQLException{
		return GestorBD.eliminarArticulo(this);
	}
	public static HashMap<String,Articulo> buscarXdescripcion(String patronBusqueda) throws SQLException{
		return GestorBD.buscarArticulosXdescripcion(patronBusqueda);
	}	
	
}
