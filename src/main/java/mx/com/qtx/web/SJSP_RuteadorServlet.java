package mx.com.qtx.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.com.qtx.dominio.Articulo;
import mx.com.qtx.dominio.GrupoPersonas;
import mx.com.qtx.dominio.Persona;
import mx.com.qtx.util.Validador;

@WebServlet("/SJSP_RuteadorServlet")
public class SJSP_RuteadorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SJSP_RuteadorServlet() throws Exception {
        super();
        Class.forName("com.mysql.jdbc.Driver");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion=request.getSession(true);
		
		String idVista = request.getParameter("fmIdVista");
		if(idVista.equals("Menu")){
			String opcion = request.getParameter("fmOpcion");
			if(opcion.equals("AltaArticulo")){
				String url = response.encodeRedirectURL("formularios/abc_articulo.jsp");
				response.sendRedirect(url);
			}
			else if(opcion.equals("ConsultaPersonas")){
				GrupoPersonas listaPersonas;
				try{
					listaPersonas = GrupoPersonas.obtenerPersonas();
				}
				catch (SQLException e) { //Falló la inserción en la base de datos
					sesion.setAttribute("mensajeError", "Oops, error de base de datos al intentar recuperar Personas");
					sesion.setAttribute("sqlState", e.getSQLState());
					String url = response.encodeRedirectURL("formularios/paginaError.jsp");
					response.sendRedirect(url);
					return;
				}
//				sesion.setAttribute("listaPersonas", listaPersonas.obtenerListaPersonas().toArray()); // Regresando un arreglo
				ArrayList<Persona> arrListaPersonas =  
					new ArrayList<Persona>(listaPersonas.obtenerListaPersonas());
				sesion.setAttribute("listaPersonas",arrListaPersonas ); // Regresando un ArrayList de Personas
				String url = response.encodeRedirectURL("formularios/"+request.getParameter("jsp"));
				response.sendRedirect(url);
			}
			else
			{
				sesion.setAttribute("mensajeError", "Opción inválida. No hay funcionalidad implementada aún");
				String url = response.encodeRedirectURL("formularios/paginaError.jsp");
				response.sendRedirect(url);
			}
			return;
		}
		if(idVista.equals("AltaArticulo")){
			String deseaRegresar = request.getParameter("fmDeseaRegresar");
			if(deseaRegresar.equals("SI")){
				sesion.invalidate();
				String raizAplicacion ="/"+ this.getServletContext().getServletContextName();
				String url = response.encodeRedirectURL(raizAplicacion);
				response.sendRedirect(url);
				return;

			}
			
			int numErrores=0;
			HashMap<String,String> tablaErrores = new HashMap<String,String>();
			Articulo nuevoArticulo=new Articulo("","",0.0f,0.0f);
			numErrores=this.validarCamposFormulario(request, tablaErrores, nuevoArticulo);
			
			if(numErrores>0){//Se encontraron errores
				sesion.setAttribute("tablaErrores", tablaErrores);
				sesion.setAttribute("articulo", nuevoArticulo);
				sesion.setAttribute("hayErrores", "Formulario con errores");
				
				String url = response.encodeRedirectURL("formularios/abc_articulo.jsp");
				response.sendRedirect(url);
				return;
			}
			
			try{
				nuevoArticulo.insertarEnBD();
			}
			catch (SQLException e) { //Falló la inserción en la base de datos
				sesion.setAttribute("mensajeError", "Oops, error de base de datos al intentar la inserción");
				sesion.setAttribute("sqlState", e.getSQLState());
				String url = response.encodeRedirectURL("formularios/paginaError.jsp");
				response.sendRedirect(url);
				return;
			}
			// Se ha insertado con éxito el registro en la base de datos
			String mensajeExito="El registro ha sido dado de alta " +
							"en la base de datos de manera exitosa";
			sesion.setAttribute("mensajeExito", mensajeExito);
			String url = response.encodeRedirectURL("formularios/paginaExito.jsp");
			response.sendRedirect(url);
			return;
		}
		if(idVista.equals("PaginaError")
				||idVista.equals("PaginaExito")
				||idVista.equals("ConsultaPersonas")){
			sesion.invalidate();
			String raizAplicacion ="/"+ this.getServletContext().getServletContextName();
			String url = response.encodeRedirectURL(raizAplicacion);
			response.sendRedirect(url);
		}
	}
	private int validarCamposFormulario(HttpServletRequest request,
				HashMap<String,String> tablaErrores,
				Articulo articulo)
	{
		int numErrores=0;
		if (this.cveArticuloEsValida(request, tablaErrores, articulo, "fmCveArticulo")==false)
			numErrores++;
		if (this.descripcionEsValida(request, tablaErrores, articulo, "fmDescripcion")==false)
			numErrores++;
		if (this.costoProv1EsValido(request, tablaErrores, articulo, "fmCostoProv1")==false)
			numErrores++;
		if (this.precioListaEsValido(request, tablaErrores, articulo, "fmPrecioLista")==false)
			numErrores++;
		return numErrores;
	}
	
	private boolean cveArticuloEsValida(HttpServletRequest request,
			HashMap<String,String> tablaErrores,
			Articulo articulo,String nombreCampo){
		String cveArticulo = request.getParameter(nombreCampo);
		articulo.setCveArticulo(cveArticulo);
		if (Validador.cadenaEnBlaco(cveArticulo)){
			tablaErrores.put(nombreCampo, "La clave no debe estar vacía");
			return false;
		}
		try{
			Articulo articuloPreExistente=null;
			articuloPreExistente=Articulo.recuperaXid(cveArticulo);
			if(articuloPreExistente!=null){
				tablaErrores.put(nombreCampo, "Ya hay un artículo con esa clave en la bd: "+articuloPreExistente);
				return false;
			}
		}
		catch (SQLException e){
				tablaErrores.put("BD", "PROBLEMA DE CONEXION A BD!!");
				return false;
		}
		return true;
	}
	private boolean descripcionEsValida(HttpServletRequest request,
			HashMap<String,String> tablaErrores,
			Articulo articulo,String nombreCampo){
		String descripcion = request.getParameter(nombreCampo);
		articulo.setDescripcion(descripcion);
		if (Validador.cadenaEnBlaco(descripcion)){
			tablaErrores.put(nombreCampo, "La descripción es obligatoria. No puede estar en blanco");
			return false;
		}
		return true;
	}
	private boolean costoProv1EsValido(HttpServletRequest request,
			HashMap<String,String> tablaErrores,
			Articulo articulo,String nombreCampo){
		float costoProv1=0.0f;
		articulo.setCostoProv1(0.0f);
		String cadCostoProv1 = request.getParameter(nombreCampo);
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
	private boolean precioListaEsValido(HttpServletRequest request,
			HashMap<String,String> tablaErrores,
			Articulo articulo,String nombreCampo){
		float precioLista=0.0f;
		articulo.setPrecioLista(0.0f);
		String cadPrecioLista = request.getParameter(nombreCampo);
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
