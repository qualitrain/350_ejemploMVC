package mx.com.qtx.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.com.qtx.dominio.Articulo;
import mx.com.qtx.persistencia.GestorBD;
import mx.com.qtx.util.Validador;

@WebServlet("/S01_AltaArticuloServlet")
public class S01_AltaArticuloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   public S01_AltaArticuloServlet() throws Exception {
        super();
        Class.forName("com.mysql.jdbc.Driver");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
		response.setContentType("text/html");
		
		PrintWriter salidaNavegador = response.getWriter();
		String htmlCabecera = this.generaCabeceraHtml();
		salidaNavegador.println(htmlCabecera);
		
		int numErrores=0;
		HashMap<String,String> tablaErrores = new HashMap<String,String>();
		Articulo nuevoArticulo=new Articulo("","",0.0f,0.0f);
		
		numErrores=
			this.obtener_ValidarCamposArticulo(request, 
						tablaErrores, nuevoArticulo);
		if(numErrores>0){//Se encontraron errores
			String htmlDeError="";
			htmlDeError=this.generaContenidoHtmlDeError(request, tablaErrores);
			salidaNavegador.println(htmlDeError);
		}
		else{
			try{
				nuevoArticulo.insertarEnBD();
			}
			 catch (SQLException e) {
				 GestorBD.mostrarSQLException(e);
				 salidaNavegador.println("<h1>Oops, error de base de datos al intentar la inserción</h1>" +
						 "<p>"+e.getMessage()+",Sql State="+e.getSQLState()+"</p>"+
				 		"\n</body>\n</html>");
				 return;
			 }
			String contenidoHtmlDeExito=this.generaContenidoHtmlDeExito();
			salidaNavegador.println(contenidoHtmlDeExito);
		}
		salidaNavegador.println("</body>\n</html>");
		
	}
	private String generaCabeceraHtml(){
		String htmlCabecera="";
		htmlCabecera+="<!DOCTYPE html>\n<html>\n<head>\n";
		htmlCabecera+="<meta charset=\"ISO-8859-1\">\n";
		htmlCabecera+="<link rel=\"stylesheet\" href=\"css/estiloFormularioCaptura.css\" type=\"text/css\" />\n";
		htmlCabecera+="<title>Consulta de personas de la BD</title>\n";
		htmlCabecera+="</head>\n<body>";
		return htmlCabecera;
		
	}
	private String generaContenidoHtmlDeExito(){
		String htmlDeFinExitoso="";
		htmlDeFinExitoso+="<p class=\"mensajeExito\">\n";
		htmlDeFinExitoso+="El registro ha sido dado de alta " +
				"en la base de datos de manera exitosa\n";
		htmlDeFinExitoso+="</p>\n";
		htmlDeFinExitoso+="<form " +
				" action=\"formularios/abc_articulo.html\" " +
				" method=\"get\">\n";
		htmlDeFinExitoso+="<input type=\"submit\" " +
				"value=\"Regresar\">\n";
		htmlDeFinExitoso+="</form>\n";
		return htmlDeFinExitoso;
	}
	private String generaContenidoHtmlDeError(HttpServletRequest request,
								HashMap<String,String> tablaErrores ){
		String htmlDeError = "";
		
		String hmtlValCveArticulo = this.recuperaHtmlValParametro(request,"fmCveArticulo");
		String htmlValDescripcion = this.recuperaHtmlValParametro(request,"fmDescripcion");
		String htmlValCostoProv1 = this.recuperaHtmlValParametro(request,"fmCostoProv1");
		String htmlValPrecioLista = this.recuperaHtmlValParametro(request,"fmPrecioLista");
		
		String htmlErrorCveArticulo=this.generaHtmlErrorCampo("fmCveArticulo", tablaErrores);
		String htmlErrorDescripcion=this.generaHtmlErrorCampo("fmDescripcion", tablaErrores);
		String htmlErrorCostoProv1=this.generaHtmlErrorCampo("fmCostoProv1", tablaErrores);
		String htmlErrorPrecioLista=this.generaHtmlErrorCampo("fmPrecioLista", tablaErrores);
		String htmlErrorBD=tablaErrores.get("BD");
		if(htmlErrorBD==null)
			htmlErrorBD="";
		
		htmlDeError+=
			"<h2>Alta de artículos</h2>\n" +
			"<form action=\"S01_AltaArticuloServlet\" " +
				"method=\"get\">\n" +
			"Clave:  " +
			"<input type=\"text\" " +
				"name=\"fmCveArticulo\"" +
				hmtlValCveArticulo +
				" size=\"20\" >" +
				htmlErrorCveArticulo +
				"<br>\n" +
			"Descripción:" +
			"<input type=\"text\" " +
				"name=\"fmDescripcion\"" +
				htmlValDescripcion +
				" size=\"40\">" + 
				htmlErrorDescripcion +
				"<br>\n" +
			"Costo Prov 1:" +
			"<input type=\"text\" " +
				"name=\"fmCostoProv1\"" +
				htmlValCostoProv1 +
				" size=\"15\">" +
				htmlErrorCostoProv1 +
				"<br>\n" +
			"Precio de Lista:" +
			"<input type=\"text\" " +
				"name=\"fmPrecioLista\"" +
				htmlValPrecioLista +
				" size=\"15\">" +
				htmlErrorPrecioLista +
				"<br>\n" +
			"<input type=\"submit\" " +
				"value=\"Dar de Alta\">\n</form>\n ";
		htmlDeError+="<p class=\"mensajeError\">"+
				"Formulario con errores "+"</p>\n";
		htmlDeError+="<p class=\"mensajeError\">"+ htmlErrorBD+"</p>\n";
		
		return htmlDeError;
	}
	String recuperaHtmlValParametro(HttpServletRequest request, String fmCampo){
		String parametro= request.getParameter(fmCampo);
		if(Validador.cadenaEnBlaco(parametro))
			return("");
		else
			return(" value=\""+parametro+"\" ");
	}
	String generaHtmlErrorCampo(String fmCampo,
						HashMap<String,String> tablaErrores){
		String htmlErrorCampo=tablaErrores.get(fmCampo);
		if (htmlErrorCampo==null)
			htmlErrorCampo="";
		else
			htmlErrorCampo="&nbsp<span class=\"mensajeError\">" +
			htmlErrorCampo + "</span>";

		return htmlErrorCampo;
	}

	private int obtener_ValidarCamposArticulo(HttpServletRequest request,
			HashMap<String,String> tablaErrores,
			Articulo articulo)
	{
		int numErrores=0;
		String cveArticulo = request.getParameter("fmCveArticulo");
		if (Validador.cadenaEnBlaco(cveArticulo)){
			numErrores++;
			tablaErrores.put("fmCveArticulo", "La clave no debe estar vacía");
		}
		else{
			try{
				Articulo articuloPreExistente=null;
				articuloPreExistente=Articulo.recuperaXid(cveArticulo);
				if(articuloPreExistente!=null){
					numErrores++;
					tablaErrores.put("fmCveArticulo", "Ya hay un artículo con esa clave en la bd: "+articuloPreExistente);
				}
				else
					articulo.setCveArticulo(cveArticulo);
			}
			catch (SQLException e){
				numErrores++;
				tablaErrores.put("BD", "PROBLEMA DE CONEXION A BD!!");
			}
		}
		
		String descripcion = request.getParameter("fmDescripcion");
		if (Validador.cadenaEnBlaco(descripcion)){
			numErrores++;
			tablaErrores.put("fmDescripcion", "La descripción es obligatoria. No puede estar en blanco");
		}
		articulo.setDescripcion(descripcion);
		
		float costoProv1=0.0f;
		String cadCostoProv1 = request.getParameter("fmCostoProv1");
		if (Validador.cadenaEnBlaco(cadCostoProv1)){
			numErrores++;
			tablaErrores.put("fmCostoProv1", "El costo prov 1 es obligatorio");
		}
		else{
			if(Validador.esFlotanteNumerico(cadCostoProv1)){
				costoProv1=Float.parseFloat(cadCostoProv1);
				articulo.setCostoProv1(costoProv1);
				if (costoProv1<=0){
					numErrores++;
					tablaErrores.put("fmCostoProv1", "El costo debe ser mayor que cero");
				}
			}
			else{	
				numErrores++;
				tablaErrores.put("fmCostoProv1", "El costo prov 1 debe ser numérico");
			}
		} 
		float precioLista=0.0f;
		String cadPrecioLista = request.getParameter("fmPrecioLista");
		if (Validador.cadenaEnBlaco(cadPrecioLista)){
			numErrores++;
			tablaErrores.put("fmPrecioLista", "El precio de lista es obligatorio");
		}
		else{
			if(Validador.esFlotanteNumerico(cadPrecioLista)){
				precioLista=Float.parseFloat(cadPrecioLista);
				articulo.setPrecioLista(precioLista);
				if (precioLista<=0){
					numErrores++;
					tablaErrores.put("fmPrecioLista", "El precio de lista debe ser mayor que cero");
				}
				else
				if (precioLista<=costoProv1){
					numErrores++;
					tablaErrores.put("fmPrecioLista", "El precio de lista debe ser mayor que el costo prov 1");
				}
			}
			else{
				numErrores++;
				tablaErrores.put("fmPrecioLista", "El precio de lista debe ser numérico");
			}
		}

		
		return numErrores;
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
